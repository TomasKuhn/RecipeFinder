package com.tkuhn.recipefinder.repository

import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.network.RecipesService
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.domain.Recipe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.inject
import org.koin.dsl.module
import retrofit2.Response

internal class RecipesRepoTest : BaseUnitTest() {

    override val testingModules = module {
        single { RecipesRepo(recipesService, db) }
    }

    private val recipesRepo: RecipesRepo by inject()
    private val recipesService: RecipesService = mockk()
    private val db: Db = mockk()

    @Test
    fun findRecipesBuNutrient() = runBlocking {
        // Given
        coEvery {
            recipesService.findRecipesByNutrient(any(), any())
        } returns Response.success(emptyList())

        // When
        val flow = recipesRepo.findRecipesBuNutrient(1, 10)
        val resources = flow.toList()

        // Then
        Truth.assertThat(resources[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(resources[1]).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(resources[1].data).isEqualTo(emptyList<Recipe>())
        coVerify { recipesService.findRecipesByNutrient(any(), any()) }
    }

    //    @GET("recipes/findByNutrients")
    //    suspend fun findRecipesByNutrient(
    //        @Query("minCalories") minCalories: Int,
    //        @Query("maxCalories") maxCalories: Int,
    //        @Query("number") number: Int = 30
    //    ): Response<List<NetworkRecipe>>

    //    @Nested
    //    @DisplayName("Recipe detail tests")
    //    inner class RecipeDetail {
    //        @Test
    //        fun getRecipeDetail() {
    //        }
    //
    //        @Test
    //        fun refreshRecipeDetail() {
    //        }
    //    }
    //
    //    @Nested
    //    @DisplayName("Recipe summary tests")
    //    inner class RecipeSummary {
    //
    //        @Test
    //        fun getRecipeSummary() {
    //        }
    //
    //        @Test
    //        fun refreshRecipeSummary() {
    //        }
    //    }

    //1. If recipe is not in cache, remote is called

    //2. If recipe is fetched, save it to cache before returning to user
    //If it's success, recipe is returned from remote
    //If it's success, recipe is stored in cached
    //If it's fail, nothing is stored to cache

    //3. If recipe is in cache, it should be returned before calling a server
    //If recipe is in cache server is not called
    //If recipe is in cache it's returned

}