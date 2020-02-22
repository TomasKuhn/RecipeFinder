package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeDetails")
data class DbRecipeDetail(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "readyInMinutes") val readyInMinutes: Int,
    @ColumnInfo(name = "sourceUrl") val sourceUrl: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "healthScore") val healthScore: Float,
    @ColumnInfo(name = "score") val score: Float
) : DbBaseData(10)