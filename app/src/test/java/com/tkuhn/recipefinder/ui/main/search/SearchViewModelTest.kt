package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.mock.RecipesRepoMock
import com.tkuhn.recipefinder.utils.extensions.toText
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
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
    @DisplayName("Test min calories and max calories validation")
    fun search_validation_error() {
        // Given
        mockkStatic("com.tkuhn.recipefinder.utils.extensions.ResourcesExtensionsKt")
        every {
            R.string.search_min_max_condition.toText()
        } returns "Fake value"
        val mockObserver = createMockObserver()
        viewModel.snackMessage.observeForever(mockObserver)

        // When
        viewModel.minCalories.value = 100.toString()
        viewModel.maxCalories.value = 10.toString()
        viewModel.search()

        // Then
        val list = mutableListOf<String>()
        verify { mockObserver.onChanged(capture(list)) }

        val errorMessage = list[0]
        Truth.assertThat(errorMessage).isNotNull()
    }

    private fun createMockObserver(): Observer<String> = spyk(Observer { })

}