package com.tkuhn.recipefinder.mock

import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary
import com.tkuhn.recipefinder.repository.RecipesRepo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow

object RecipesRepoMock {

    val recipeId = 1L
    val mockRecipe = RecipeDetail(
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
    val mockRecipeSummary = RecipeSummary(
        "title",
        "summary",
        true
    )
    val successResourceRecipe = Resource.Success(mockRecipe)
    val errorResourceRecipe = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))
    val successResourceRecipeSummary = Resource.Success(mockRecipeSummary)
    val errorResourceRecipeSummary = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))

    val mock = mockk<RecipesRepo> {
        every {
            getRecipeDetail(recipeId)
        } returns flow {
            emit(successResourceRecipe)
        }

        every {
            getRecipeSummary(recipeId)
        } returns flow {
            emit(successResourceRecipeSummary)
        }
    }
}