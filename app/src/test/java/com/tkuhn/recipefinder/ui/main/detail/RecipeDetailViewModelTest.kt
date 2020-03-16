package com.tkuhn.recipefinder.ui.main.detail

import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.getValues
import com.tkuhn.recipefinder.mock.RecipesRepoMock
import com.tkuhn.recipefinder.mockObserver
import org.junit.jupiter.api.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

internal class RecipeDetailViewModelTest : BaseUnitTest() {

    override val testingModules = module {
        viewModel { RecipeDetailViewModel(RecipesRepoMock.recipeId, recipesRepo) }
    }

    private val recipesRepo = RecipesRepoMock.mock
    private val viewModel: RecipeDetailViewModel by inject()

    @Test
    fun searchMinMaxException() {
        // Given
        val uiRecipe = UiRecipeDetail.create(RecipesRepoMock.mockRecipe)
        val mockObserver = viewModel.recipeDetail.mockObserver()

        // Then
        val values = mockObserver.getValues()
        Truth.assertThat(values[0]).isEqualTo(uiRecipe)
    }

    @Test
    fun getRecipeDetail() {
    }

    @Test
    fun getRecipeSummary() {
    }

    @Test
    fun isRefreshing() {
    }

    @Test
    fun refresh() {
    }
}