package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Forecast")
data class DbForecast(
    @PrimaryKey
    @ColumnInfo(name = "cod") val cod: String
)