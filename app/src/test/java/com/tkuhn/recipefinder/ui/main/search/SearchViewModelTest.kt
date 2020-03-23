package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.MockData
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.getValues
import com.tkuhn.recipefinder.mockObserver
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.utils.extensions.toText
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

internal class SearchViewModelTest : BaseUnitTest() {

    companion object {
        private val recipesRepo: RecipesRepo = mockk()
        private val mockRecipe = MockData.createRecipe()
    }

    override val testingModules = module {
        viewModel { SearchViewModel(savedStateHandle, recipesRepo) }
    }

    private val viewModel: SearchViewModel by inject()
    private val savedStateHandle = SavedStateHandle()

    @Test
    fun `show empty min calories error on search`() {
        // Given
        val expectedErrorMessage = "Can't be empty"
        mockResources()
        every { R.string.err_empty_field.toText() } returns expectedErrorMessage
        val minCaloriesErrorObserver = viewModel.minCaloriesError.mockObserver()

        // When
        viewModel.minCalories.value = ""
        viewModel.search()

        // Then
        val minCaloriesError = minCaloriesErrorObserver.getValues()[0]
        assertThat(minCaloriesError).isEqualTo(expectedErrorMessage)
    }

    @Test
    fun `show max is lower than min error on search`() {
        // Given
        val expectedErrorMessage = "Max must be greater then min"
        mockResources()
        every { R.string.search_min_max_condition.toText() } returns expectedErrorMessage
        val snackMessageObserver = viewModel.snackMessage.mockObserver()

        // When
        viewModel.minCalories.value = 100.toString()
        viewModel.maxCalories.value = 10.toString()
        viewModel.search()

        // Then
        val snackMessage = snackMessageObserver.getValues()[0]
        assertThat(snackMessage).isEqualTo(expectedErrorMessage)
    }

    @Test
    fun `search caused unexpected error`() {
        // Given
        val expectedErrorMessage = "Unexpected error"
        val snackMessageObserver = viewModel.snackMessage.mockObserver()
        every {
            recipesRepo.findRecipesBuNutrient(any(), any())
        } returns flowOf(Resource.Error(ResourceError.UnexpectedError(expectedErrorMessage)))

        // When
        val min = 10
        val max = 100
        viewModel.minCalories.value = min.toString()
        viewModel.maxCalories.value = max.toString()
        viewModel.search()

        // Then
        val snackMessage = snackMessageObserver.getValues()[0]
        assertThat(snackMessage).isEqualTo(expectedErrorMessage)
        verify { recipesRepo.findRecipesBuNutrient(min, max) }
    }

    @Test
    fun `successfully search recipes`() {
        // Given
        val recipesObserver = viewModel.recipes.mockObserver()
        every {
            recipesRepo.findRecipesBuNutrient(any(), any())
        } returns flowOf(Resource.Success(listOf(mockRecipe)))

        // When
        val min = 10
        val max = 100
        viewModel.minCalories.value = min.toString()
        viewModel.maxCalories.value = max.toString()
        viewModel.search()

        // Then
        val recipes = recipesObserver.getValues()[0]
        assertThat(recipes).isNotEmpty()
        verify { recipesRepo.findRecipesBuNutrient(min, max) }
    }

}