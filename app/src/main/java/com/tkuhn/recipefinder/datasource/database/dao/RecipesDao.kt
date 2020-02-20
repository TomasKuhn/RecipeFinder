package com.tkuhn.recipefinder.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeDetail
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipeDetail(recipeDetail: DbRecipeDetail)

    @Query("SELECT * FROM RecipeDetails WHERE id = :recipeId")
    abstract suspend fun getRecipeDetail(recipeId: Long): DbRecipeDetail?

    @Query("SELECT * FROM RecipeDetails WHERE id = :recipeId")
    abstract fun getRecipeDetailFlow(recipeId: Long): Flow<DbRecipeDetail>
}