package com.tkuhn.recipefinder.repository

import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.network.NetworkBoundResource
import com.tkuhn.recipefinder.datasource.network.NetworkCall
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.api.RecipesApiService
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeSummary
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary
import com.tkuhn.recipefinder.repository.mapper.RecipeMapper
import com.tkuhn.recipefinder.repository.mapper.toDbRecipeDetail
import com.tkuhn.recipefinder.repository.mapper.toDbRecipeSummary
import com.tkuhn.recipefinder.repository.mapper.toRecipeDetail
import com.tkuhn.recipefinder.repository.mapper.toRecipeSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class RecipesRepo(
    private val apiService: RecipesApiService,
    private val db: Db
) {

    private var recipeDetailResource: NetworkBoundResource<RecipeDetail, NetworkRecipeDetail>? = null
    private var recipeSummaryResource: NetworkBoundResource<RecipeSummary, NetworkRecipeSummary>? = null

    fun findRecipesBuNutrient(minCalories: Int, maxCalories: Int): Flow<Resource<List<Recipe>>> {
        return object : NetworkCall<List<NetworkRecipe>, List<Recipe>>() {
            override suspend fun createCall(): Response<List<NetworkRecipe>> {
                return apiService.findRecipesByNutrient(minCalories, maxCalories)
            }

            override fun convertResponse(response: List<NetworkRecipe>): List<Recipe> {
                return RecipeMapper.networkToDomainList.map(response)
            }
        }.execute()
    }

    fun getRecipeDetail(recipeId: Long): Flow<Resource<RecipeDetail>> {
        recipeDetailResource = object : NetworkBoundResource<RecipeDetail, NetworkRecipeDetail>() {
            override suspend fun saveCallResult(item: NetworkRecipeDetail) {
                val dbRecipeDetail = item.toDbRecipeDetail()
                db.recipesDao().insertRecipeDetail(dbRecipeDetail)
            }

            override fun shouldFetch(data: RecipeDetail?): Boolean {
                return data?.isValid != true
            }

            override fun loadFromDb(): Flow<RecipeDetail?> {
                return db.recipesDao().getRecipeDetail(recipeId).map {
                    it?.toRecipeDetail()
                }
            }

            override suspend fun createCall(): Response<NetworkRecipeDetail> {
                return apiService.getRecipeDetail(recipeId)
            }
        }
        return recipeDetailResource!!.loadData()
    }

    fun refreshRecipeDetail(): Flow<Resource<RecipeDetail>>? {
        return recipeDetailResource?.refresh()
    }

    fun getRecipeSummary(recipeId: Long): Flow<Resource<RecipeSummary>> {
        recipeSummaryResource = object : NetworkBoundResource<RecipeSummary, NetworkRecipeSummary>() {
            override suspend fun saveCallResult(item: NetworkRecipeSummary) {
                val dbRecipeSummary = item.toDbRecipeSummary()
                db.recipesDao().insertRecipeSummary(dbRecipeSummary)
            }

            override fun shouldFetch(data: RecipeSummary?): Boolean {
                return data?.isValid != true
            }

            override fun loadFromDb(): Flow<RecipeSummary?> {
                return db.recipesDao().getRecipeSummary(recipeId).map {
                    it?.toRecipeSummary()
                }
            }

            override suspend fun createCall(): Response<NetworkRecipeSummary> {
                return apiService.getRecipeSummary(recipeId)
            }
        }
        return recipeSummaryResource!!.loadData()
    }

    fun refreshRecipeSummary(): Flow<Resource<RecipeSummary>>? {
        return recipeSummaryResource?.refresh()
    }
}