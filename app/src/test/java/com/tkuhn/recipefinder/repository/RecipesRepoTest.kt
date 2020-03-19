package com.tkuhn.recipefinder.repository

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Recipes repository tests")
internal class RecipesRepoTest {

    @Test
    fun findRecipesBuNutrient() {
    }

    @Nested
    @DisplayName("Recipe detail tests")
    inner class RecipeDetail {
        @Test
        fun getRecipeDetail() {
        }

        @Test
        fun refreshRecipeDetail() {
        }
    }

    @Nested
    @DisplayName("Recipe summary tests")
    inner class RecipeSummary {

        @Test
        fun getRecipeSummary() {
        }

        @Test
        fun refreshRecipeSummary() {
        }
    }

    //1. If recipe is not in cache, remote is called

    //2. If recipe is fetched, save it to cache before returning to user
    //If it's success, recipe is returned from remote
    //If it's success, recipe is stored in cached
    //If it's fail, nothing is stored to cache

    //3. If recipe is in cache, it should be returned before calling a server
    //If recipe is in cache server is not called
    //If recipe is in cache it's returned

}