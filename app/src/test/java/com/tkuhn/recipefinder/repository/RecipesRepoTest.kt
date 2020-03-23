package com.tkuhn.recipefinder.repository

import com.google.common.truth.Truth.assertThat
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.MockData
import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.database.dao.RecipesDao
import com.tkuhn.recipefinder.datasource.network.RecipesService
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.repository.mapper.toDbRecipeDetail
import com.tkuhn.recipefinder.repository.mapper.toRecipeDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.dsl.module
import retrofit2.Response

internal class RecipesRepoTest : BaseUnitTest() {

    companion object {
        private val recipesService: RecipesService = mockk()
        private val db: Db = mockk()
        private val recipesDao: RecipesDao = mockk(relaxed = true)
        private val recipeDetailErrorResponse = mockk<Response<NetworkRecipeDetail>>()
    }

    override val testingModules = module {
        single { RecipesRepo(recipesService, db) }
    }

    private val recipesRepo: RecipesRepo by inject()

    @Before
    fun initMocks() {
        every { db.recipesDao() } returns recipesDao
        recipeDetailErrorResponse.run {
            every { code() } returns 404
            every { message() } returns "Recipe detail wasn't found"
            every { isSuccessful } returns false
        }
    }

    @Test
    fun `load empty recipes`() = runBlocking {
        // Given
        coEvery {
            recipesService.findRecipesByNutrient(any(), any())
        } returns Response.success(emptyList())

        // When
        val flow = recipesRepo.findRecipesBuNutrient(1, 10)
        val resources = flow.toList()

        // Then
        assertThat(resources).containsExactly(
            Resource.Loading<List<Recipe>>(),
            Resource.Success<List<Recipe>>(emptyList())
        )
        coVerify { recipesService.findRecipesByNutrient(any(), any()) }
    }

    @Test
    fun `If recipe is not in cache, remote is called`() = runBlocking {
        // Given
        val recipeId = 123L
        coEvery {
            recipesDao.getRecipeDetail(eq(recipeId))
        } returns flowOf(null)
        coEvery {
            recipesService.getRecipeDetail(eq(recipeId))
        } returns Response.success(MockData.createNetworkRecipeDetail(id = recipeId))

        // When
        recipesRepo.getRecipeDetail(recipeId).collect()

        // Then
        coVerify { recipesDao.getRecipeDetail(eq(recipeId)) }
        coVerify { recipesService.getRecipeDetail(eq(recipeId)) }
    }

    @Test
    fun `If recipe is not in cache, download it and save it to cache`() = runBlocking {
        // Given
        val recipeId = 123L
        val networkRecipeDetail = MockData.createNetworkRecipeDetail(id = recipeId)
        coEvery {
            recipesService.getRecipeDetail(eq(recipeId))
        } returns Response.success(networkRecipeDetail)
        coEvery {
            recipesDao.getRecipeDetail(eq(recipeId))
        } returns flowOf(null, networkRecipeDetail.toDbRecipeDetail())

        // When
        val resources = recipesRepo.getRecipeDetail(recipeId).toList()

        // Then
        val expectedDbRecipeDetail = networkRecipeDetail.toDbRecipeDetail()
        val expectedRecipeDetail = expectedDbRecipeDetail.toRecipeDetail()
        assertThat(resources).containsExactly(
            Resource.Loading<RecipeDetail>(),
            Resource.Success(expectedRecipeDetail)
        )
        coVerifyOrder {
            recipesDao.getRecipeDetail(eq(recipeId))
            recipesService.getRecipeDetail(eq(recipeId))
            recipesDao.insertRecipeDetail(eq(expectedDbRecipeDetail))
        }
    }

    @Test
    fun `If load recipe detail fails, nothing is stored to cache`() = runBlocking {
        // Given
        val recipeId = 123L
        coEvery {
            recipesDao.getRecipeDetail(eq(recipeId))
        } returns flowOf(null)
        coEvery {
            recipesService.getRecipeDetail(eq(recipeId))
        } returns recipeDetailErrorResponse

        // When
        val resources = recipesRepo.getRecipeDetail(recipeId).toList()

        // Then
        assertThat(resources).containsExactly(
            Resource.Loading<RecipeDetail>(),
            Resource.Error<RecipeDetail>(ResourceError.HttpError(recipeDetailErrorResponse.message(), recipeDetailErrorResponse.code()))
        )
        coVerifyOrder {
            recipesDao.getRecipeDetail(eq(recipeId))
            recipesService.getRecipeDetail(eq(recipeId))
        }
        coVerify(exactly = 0) { recipesDao.insertRecipeDetail(any()) }
    }

    @Test
    fun `If recipe is in cache, it should be returned before calling a server`() = runBlocking {
        // Given
        val recipeId = 123L
        val networkRecipeDetail = MockData.createNetworkRecipeDetail(id = recipeId)
        val dbRecipeDetail = networkRecipeDetail.toDbRecipeDetail()
        coEvery {
            recipesDao.getRecipeDetail(eq(recipeId))
        } returns flowOf(dbRecipeDetail)
        coEvery {
            recipesService.getRecipeDetail(eq(recipeId))
        } returns recipeDetailErrorResponse

        // When
        val resources = recipesRepo.getRecipeDetail(recipeId).toList()

        // Then
        val expectedRecipeDetail = dbRecipeDetail.toRecipeDetail()
        assertThat(resources).containsExactly(
            Resource.Loading<RecipeDetail>(),
            Resource.Success(expectedRecipeDetail)
        )
        verify { recipesDao.getRecipeDetail(eq(recipeId)) }
        coVerify(exactly = 0) { recipesService.getRecipeDetail(eq(recipeId)) }
    }
}