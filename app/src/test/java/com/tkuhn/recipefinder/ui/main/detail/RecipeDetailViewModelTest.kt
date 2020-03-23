package com.tkuhn.recipefinder.ui.main.detail

import com.google.common.truth.Truth.assertThat
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.MockData
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.getValues
import com.tkuhn.recipefinder.mockObserver
import com.tkuhn.recipefinder.repository.RecipesRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

internal class RecipeDetailViewModelTest : BaseUnitTest() {

    companion object {
        private const val RECIPE_ID = 30078L
        private val recipesRepo: RecipesRepo = mockk()
        private val mockRecipeDetail = MockData.createRecipeDetail(id = RECIPE_ID)
        private val mockRecipeSummary = MockData.createRecipeSummary()
    }

    override val testingModules = module {
        viewModel { RecipeDetailViewModel(RECIPE_ID, recipesRepo) }
    }
    private val viewModel: RecipeDetailViewModel by inject()

    @Test
    fun `download recipe detail on initialization`() {
        // Given
        recipeDetailAndSummaryMocks()
        val expectedRecipeDetail = UiRecipeDetail.create(mockRecipeDetail)
        val recipeDetailObserver = viewModel.uiRecipeDetail.mockObserver()

        // Then
        val values = recipeDetailObserver.getValues(timeout = 300)
        assertThat(values[0]).isEqualTo(expectedRecipeDetail)
        verify { recipesRepo.getRecipeDetail(RECIPE_ID) }
    }

    @Test
    fun `download recipe summary on initialization`() {
        // Given
        recipeDetailAndSummaryMocks()
        val summaryObserver = viewModel.recipeSummary.mockObserver()

        // Then
        val expectedSummary = mockRecipeSummary.summary
        val values = summaryObserver.getValues(timeout = 300)
        assertThat(values[0]).isEqualTo(expectedSummary)
        verify { recipesRepo.getRecipeSummary(RECIPE_ID) }
    }

    @Test
    fun `isLoading is true during refresh and false on finish`() {
        // Given
        recipeDetailAndSummaryMocks()
        every {
            recipesRepo.refreshRecipeDetail()
        } returns flow {
            emit(Resource.Loading())
            delay(400)
            emit(Resource.Success(mockRecipeDetail))
        }

        every {
            recipesRepo.refreshRecipeSummary()
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeSummary))
        }
        val isRefreshingObserver = viewModel.isRefreshing.mockObserver()

        // When
        viewModel.refresh()

        // Then
        val values = isRefreshingObserver.getValues(timeout = 1000, exactly = 2)
        assertThat(values.first()).isTrue()
        assertThat(values.last()).isFalse()
        verify { recipesRepo.refreshRecipeDetail() }
        verify { recipesRepo.refreshRecipeSummary() }
    }

    private fun recipeDetailAndSummaryMocks() {
        every {
            recipesRepo.getRecipeDetail(RECIPE_ID)
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeDetail))
        }

        every {
            recipesRepo.getRecipeSummary(RECIPE_ID)
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeSummary))
        }
    }
}