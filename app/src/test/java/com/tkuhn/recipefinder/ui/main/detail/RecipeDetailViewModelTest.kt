package com.tkuhn.recipefinder.ui.main.detail

import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.getValues
import com.tkuhn.recipefinder.mock.RecipesRepoMock
import com.tkuhn.recipefinder.mockObserver
import io.mockk.verify
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

internal class RecipeDetailViewModelTest : BaseUnitTest() {

    override val testingModules = module {
        viewModel { RecipeDetailViewModel(recipeId, recipesRepo) }
    }
    private val recipeId = RecipesRepoMock.recipeId
    private var recipesRepo = RecipesRepoMock.mock
    private val viewModel: RecipeDetailViewModel by inject()

    @Test
    fun recipeDetail_success_recipeDetailDownloaded() {
        // Given
        val uiRecipe = UiRecipeDetail.create(RecipesRepoMock.mockRecipeDetail)
        val recipeDetailObserver = viewModel.uiRecipeDetail.mockObserver()

        // Then
        val values = recipeDetailObserver.getValues(timeout = 300)
        Truth.assertThat(values[0]).isEqualTo(uiRecipe)
        verify { recipesRepo.getRecipeDetail(recipeId) }
    }

    @Test
    fun recipeSummary_success_recipeSummaryDownloaded() {
        // Given
        val recipeSummary = RecipesRepoMock.mockRecipeSummary
        val summaryObserver = viewModel.recipeSummary.mockObserver()

        // Then
        val values = summaryObserver.getValues(timeout = 300)
        Truth.assertThat(values[0]).isEqualTo(recipeSummary.summary)
        verify { recipesRepo.getRecipeSummary(recipeId) }
    }

    @Test
    fun isRefreshing_success_changed() {
        // Given
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
}