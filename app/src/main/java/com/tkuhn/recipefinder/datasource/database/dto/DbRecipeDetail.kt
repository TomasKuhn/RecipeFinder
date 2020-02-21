package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeDetails")
data class DbRecipeDetail(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String
) : DbBaseData(10)