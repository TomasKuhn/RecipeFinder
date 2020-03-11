package com.tkuhn.recipefinder.ui.main.search

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.tkuhn.recipefinder.BaseUnitTest
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary
import com.tkuhn.recipefinder.getOrAwaitValue
import com.tkuhn.recipefinder.repository.RecipesRepo
import com.tkuhn.recipefinder.ui.main.detail.RecipeDetailViewModel
import com.tkuhn.recipefinder.ui.main.detail.UiRecipeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.dsl.module

class SearchViewModelTest : BaseUnitTest() {

    override val testingModules = module {
        viewModel { RecipeDetailViewModel(recipeId, recipesRepo) }
        viewModel { (handle: SavedStateHandle) -> SearchViewModel(handle, recipesRepo) }
    }

    private lateinit var recipesRepo: RecipesRepo
    private val recipeDetailViewModel: RecipeDetailViewModel by inject()
    private val viewModel: SearchViewModel by inject()

    private val recipeId = 1L
    private val invalidRecipeId = 2
    private val mockRecipe = RecipeDetail(
        recipeId,
        "title",
        "",
        123,
        "",
        12,
        33f,
        3f,
        listOf("Flavor", "Meat"),
        true
    )
    private val mockRecipeSummary = RecipeSummary(
        "title",
        "summary",
        true
    )
    private val successResourceRecipe = Resource.Success(mockRecipe)
    private val errorResourceRecipe = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))
    private val successResourceRecipeSummary = Resource.Success(mockRecipeSummary)
    private val errorResourceRecipeSummary = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @BeforeAll
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        recipesRepo = mock {
            on {
                getRecipeDetail(recipeId)
            } doReturn flow {
                emit(successResourceRecipe)
            }

            on {
                getRecipeSummary(recipeId)
            } doReturn flow {
                emit(successResourceRecipeSummary)
            }
        }
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @AfterAll
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun simpleTest() {
        assertThat(errorResourceRecipe.error.message).isEqualTo("R.string..toText(context)")
    }

    @Test
    fun searchMinMaxException() {
        val uiRecipe = UiRecipeDetail.create(mockRecipe)
        val recipeDetail = recipeDetailViewModel.recipeDetail.getOrAwaitValue()
        assertThat(recipeDetail).isEqualTo(uiRecipe)
    }
}