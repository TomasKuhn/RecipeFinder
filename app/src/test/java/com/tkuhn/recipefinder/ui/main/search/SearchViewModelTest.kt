package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.tkuhn.recipefinder.*
import com.tkuhn.recipefinder.mock.RecipesRepoMock
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
    fun getMinCalories() {

    }

    @Test
    fun getMaxCalories() {

    }

    @Test
    fun getMinCaloriesError() {

    }

    @Test
    fun getMaxCaloriesError() {

    }

    @Test
    fun getRecipes() {

    }

    @Test
    fun getNoRecipesFound() {

    }

    @Test
    fun getOnItemClick() {

    }

    @Test
    fun getShowRecipeDetailEvent() {

    }

    @Test
    fun search_error_maxIsLowerThanMin() {
        // Given
        R.string.search_min_max_condition.mockResource("Max must be greater then min")
        val mockObserver = viewModel.snackMessage.mockObserver()

        // When
        viewModel.minCalories.value = 100.toString()
        viewModel.maxCalories.value = 10.toString()
        viewModel.search()

        // Then
        val values = mockObserver.getValues()
        val errorMessage = values[0]
        Truth.assertThat(errorMessage).isNotNull()
    }

}