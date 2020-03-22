package com.tkuhn.recipefinder.ui.main.detail

import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary
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

        private val mockRecipeDetail = RecipeDetail(
            RECIPE_ID,
            "Yuzu Dipping Sauce",
            "https://spoonacular.com/recipeImages/30078-556x370.jpg",
            2,
            "http://www.marthastewart.com/315027/yuzu-dipping-sauce",
            0,
            0f,
            4f,
            emptyList(),
            true
        )
        private val mockRecipeSummary = RecipeSummary(
            "Yuzu Dipping Sauce",
            "If you have roughly <b>2 minutes</b> to spend in the kitchen...",
            true
        )
    }

    override val testingModules = module {
        viewModel { RecipeDetailViewModel(RECIPE_ID, recipesRepo) }
    }
    private val viewModel: RecipeDetailViewModel by inject()

    @Test
    fun `download recipe detail on initialization`() {
        // Given
        recipeDetailAndSummaryMocks()
        val uiRecipe = UiRecipeDetail.create(mockRecipeDetail)
        val recipeDetailObserver = viewModel.uiRecipeDetail.mockObserver()

        // Then
        val values = recipeDetailObserver.getValues(timeout = 300)
        Truth.assertThat(values[0]).isEqualTo(uiRecipe)
        verify { recipesRepo.getRecipeDetail(RECIPE_ID) }
    }

    @Test
    fun `download recipe summary on initialization`() {
        // Given
        recipeDetailAndSummaryMocks()
        val summaryObserver = viewModel.recipeSummary.mockObserver()

        // Then
        val values = summaryObserver.getValues(timeout = 300)
        Truth.assertThat(values[0]).isEqualTo(mockRecipeSummary.summary)
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
        Truth.assertThat(values.first()).isTrue()
        Truth.assertThat(values.last()).isFalse()
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