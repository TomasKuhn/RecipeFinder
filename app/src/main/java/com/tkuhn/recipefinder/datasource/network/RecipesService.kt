package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {

    @GET("recipes/findByNutrients")
    suspend fun findRecipesByNutrient(
        @Query("minCalories") minCalories: Int,
        @Query("maxCalories") maxCalories: Int
    ): Response<List<NetworkRecipe>>
}