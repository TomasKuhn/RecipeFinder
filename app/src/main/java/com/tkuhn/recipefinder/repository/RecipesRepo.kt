package com.tkuhn.recipefinder.repository

import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.network.NetworkBoundResource
import com.tkuhn.recipefinder.datasource.network.NetworkCall
import com.tkuhn.recipefinder.datasource.network.RecipesService
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.repository.mapper.RecipeDetailMapper
import com.tkuhn.recipefinder.repository.mapper.RecipeMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RecipesRepo(
    private val service: RecipesService,
    private val db: Db
) {

    private val recipeDetailValidator = ExpirationValidator<RecipeDetail>(10, TimeUnit.MINUTES)

    fun findRecipesBuNutrient(minCalories: Int, maxCalories: Int): Flow<Resource<List<Recipe>>> {
        return object : NetworkCall<List<NetworkRecipe>, List<Recipe>>() {
            override suspend fun createCall(): Response<List<NetworkRecipe>> {
                return service.findRecipesByNutrient(minCalories, maxCalories)
            }

            override fun convertResponse(response: List<NetworkRecipe>): List<Recipe> {
                return RecipeMapper.networkToDomainList.map(response)
            }
        }.execute()
    }

    fun getRecipeDetail(recipeId: Long): Flow<Resource<RecipeDetail>> {
        return object : NetworkBoundResource<RecipeDetail, NetworkRecipeDetail>() {
            override suspend fun saveCallResult(item: NetworkRecipeDetail) {
                val dbRecipeDetail = RecipeDetailMapper.networkToDb.map(item)
                db.recipesDao().insertRecipeDetail(dbRecipeDetail)
            }

            override fun shouldFetch(data: RecipeDetail?): Boolean {
                return data == null || recipeDetailValidator.shouldFetch(data)
            }

            override fun loadFlowFromDb(): Flow<RecipeDetail> {
                return db.recipesDao().getRecipeDetailFlow(recipeId).map {
                    RecipeDetailMapper.dbToDomain.map(it)
                }
            }

            override suspend fun loadFromDb(): RecipeDetail? {
                return db.recipesDao().getRecipeDetail(recipeId)?.let { dto ->
                    RecipeDetailMapper.dbToDomain.map(dto)
                }
            }

            override suspend fun createCall(): Response<NetworkRecipeDetail> {
                return service.getRecipeDetail(recipeId)
            }
        }.loadData()
    }
}