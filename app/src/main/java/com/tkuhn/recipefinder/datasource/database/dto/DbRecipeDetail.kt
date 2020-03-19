package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tkuhn.recipefinder.datasource.database.converter.RecipeDetailDbConverters
import com.tkuhn.recipefinder.domain.Ingredient

@Entity(tableName = "RecipeDetails")
@TypeConverters(RecipeDetailDbConverters::class)
data class DbRecipeDetail(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "readyInMinutes") val readyInMinutes: Int,
    @ColumnInfo(name = "sourceUrl") val sourceUrl: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "healthScore") val healthScore: Float,
    @ColumnInfo(name = "score") val score: Float,
    @ColumnInfo(name = "ingredients") val ingredients: List<Ingredient>
) : DbBaseData(3600)