package com.tkuhn.recipefinder.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeDetail
import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeSummary
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipeDetail(recipeDetail: DbRecipeDetail)

    @Query("SELECT * FROM RecipeDetails WHERE id = :recipeId")
    abstract suspend fun getRecipeDetail(recipeId: Long): DbRecipeDetail?

    @Query("SELECT * FROM RecipeDetails WHERE id = :recipeId")
    abstract fun getRecipeDetailFlow(recipeId: Long): Flow<DbRecipeDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipeSummary(recipeSummary: DbRecipeSummary)

    @Query("SELECT * FROM RecipeSummaries WHERE id = :summaryId")
    abstract suspend fun getRecipeSummary(summaryId: Long): DbRecipeSummary?

    @Query("SELECT * FROM RecipeSummaries WHERE id = :summaryId")
    abstract fun getRecipeSummaryFlow(summaryId: Long): Flow<DbRecipeSummary>
}