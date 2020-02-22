package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeSummary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesService {

    @GET("recipes/findByNutrients")
    suspend fun findRecipesByNutrient(
        @Query("minCalories") minCalories: Int,
        @Query("maxCalories") maxCalories: Int,
        @Query("number") number: Int = 30
    ): Response<List<NetworkRecipe>>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(@Path("id") recipeId: Long): Response<NetworkRecipeDetail>

    @GET("recipes/{id}/summary")
    suspend fun getRecipeSummary(@Path("id") recipeId: Long): Response<NetworkRecipeSummary>
}