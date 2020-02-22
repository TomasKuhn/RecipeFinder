package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeSummaries")
data class DbRecipeSummary(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "summary") val summary: String
) : DbBaseData(10)