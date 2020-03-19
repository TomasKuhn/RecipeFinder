package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.getValues
import com.tkuhn.recipefinder.mock.RecipesRepoMock
import com.tkuhn.recipefinder.mockObserver
import com.tkuhn.recipefinder.utils.extensions.toText
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

internal class SearchViewModelTest : BaseUnitTest() {

    override val testingModules = module {
        viewModel { SearchViewModel(savedStateHandle, recipesRepo) }
    }

    private val savedStateHandle = SavedStateHandle()
    private val recipesRepo = RecipesRepoMock.mock
    private val viewModel: SearchViewModel by inject()

    @Test
    fun search_error_minIsEmpty() {
        // Given
        val errorMessage = "Can't be empty"
        mockResources()
        every { R.string.err_empty_field.toText() } returns errorMessage
        val minCaloriesErrorObserver = viewModel.minCaloriesError.mockObserver()

        // When
        viewModel.minCalories.value = ""
        viewModel.search()

        // Then
        val minCaloriesError = minCaloriesErrorObserver.getValues()[0]
        Truth.assertThat(minCaloriesError).isEqualTo(errorMessage)
    }

    @Test
    fun search_error_maxIsLowerThanMin() {
        // Given
        val errorMessage = "Max must be greater then min"
        mockResources()
        every { R.string.search_min_max_condition.toText() } returns errorMessage
        val snackMessageObserver = viewModel.snackMessage.mockObserver()

        // When
        viewModel.minCalories.value = 100.toString()
        viewModel.maxCalories.value = 10.toString()
        viewModel.search()

        // Then
        val snackMessage = snackMessageObserver.getValues()[0]
        Truth.assertThat(snackMessage).isEqualTo(errorMessage)
    }

    @Test
    fun search_success_recipeAreLoaded() {
        // Given
        val recipesObserver = viewModel.recipes.mockObserver()

        // When
        val min = 10
        val max = 100
        viewModel.minCalories.value = min.toString()
        viewModel.maxCalories.value = max.toString()
        viewModel.search()

        // Then
        val recipes = recipesObserver.getValues()[0]
        Truth.assertThat(recipes).isNotEmpty()
        verify { recipesRepo.findRecipesBuNutrient(min, max) }
    }

}