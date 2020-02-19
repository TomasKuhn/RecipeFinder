package com.tkuhn.recipefinder.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tkuhn.recipefinder.datasource.database.dto.DbForecast
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertForecast(forecast: DbForecast)

    @Query("SELECT * FROM Forecast")
    abstract fun getForecastFlow(): Flow<DbForecast?>

    @Query("SELECT * FROM Forecast")
    abstract suspend fun getForecast(): DbForecast?

}