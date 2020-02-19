package com.tkuhn.recipefinder.cache

import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.domain.Forecast
import com.tkuhn.recipefinder.repository.mapper.ForecastMappers

class ForecastCacheData(private val mappers: ForecastMappers, database: Db) :
    BaseCacheData<Forecast>(database) {

    override suspend fun load(database: Db): Forecast? {
        return database.forecastDao().getForecast()?.let {
            mappers.dbToDomain.map(it)
        }
    }
}