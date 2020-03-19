package com.tkuhn.recipefinder.mock

import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.ResourceError
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary
import com.tkuhn.recipefinder.repository.RecipesRepo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object RecipesRepoMock {

    val recipeId = 1L
    val mockRecipe = Recipe(
        recipeId,
        "title",
        "",
        123,
        "12",
        "12",
        "33f"
    )
    val mockRecipeDetail = RecipeDetail(
        recipeId,
        "title",
        "",
        123,
        "",
        12,
        33f,
        3f,
        emptyList(),
        true
    )
    val mockRecipeSummary = RecipeSummary(
        "title",
        "summary",
        true
    )
    val errorResourceRecipe = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))
    val errorResourceRecipeSummary = Resource.Error<Recipe>(ResourceError.UnexpectedError("error"))

    val mock = mockk<RecipesRepo> {
        every {
            getRecipeDetail(recipeId)
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeDetail))
        }

        every {
            getRecipeSummary(recipeId)
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeSummary))
        }

        every {
            findRecipesBuNutrient(any(), any())
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(listOf(mockRecipe)))
        }

        every {
            refreshRecipeDetail()
        } returns flow {
            emit(Resource.Loading())
            delay(400)
            emit(Resource.Success(mockRecipeDetail))
        }

        every {
            refreshRecipeSummary()
        } returns flow {
            emit(Resource.Loading())
            delay(200)
            emit(Resource.Success(mockRecipeSummary))
        }
    }
}