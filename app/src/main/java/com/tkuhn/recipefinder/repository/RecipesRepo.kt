package com.tkuhn.recipefinder.repository

import com.tkuhn.recipefinder.datasource.network.NetworkCall
import com.tkuhn.recipefinder.datasource.network.RecipesService
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.repository.mapper.RecipeMapper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RecipesRepo(
    private val service: RecipesService,
    private val recipeMapper: RecipeMapper
) {

    fun findRecipesBuNutrient(minCalories: Int, maxCalories: Int): Flow<Resource<List<Recipe>>> {
        return object : NetworkCall<List<NetworkRecipe>, List<Recipe>>() {
            override suspend fun createCall(): Response<List<NetworkRecipe>> {
                return service.findRecipesByNutrient(minCalories, maxCalories)
            }

            override fun convertResponse(response: List<NetworkRecipe>): List<Recipe>? {
                return response.map {
                    recipeMapper.networkToDomain.map(it)
                }
            }
        }.execute()
    }
}