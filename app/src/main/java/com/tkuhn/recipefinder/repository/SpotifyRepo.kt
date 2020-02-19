package com.tkuhn.recipefinder.repository

import com.tkuhn.recipefinder.datasource.database.Db
import com.tkuhn.recipefinder.datasource.network.NetworkBoundResource
import com.tkuhn.recipefinder.datasource.network.NetworkCall
import com.tkuhn.recipefinder.datasource.network.Resource
import com.tkuhn.recipefinder.datasource.network.SpotifyService
import com.tkuhn.recipefinder.datasource.network.dto.NetworkForecast
import com.tkuhn.recipefinder.domain.Forecast
import com.tkuhn.recipefinder.repository.mapper.ForecastMappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import retrofit2.Response
import java.util.concurrent.TimeUnit


class SpotifyRepo(
    private val service: SpotifyService,
    private val db: Db,
    private val forecastMappers: ForecastMappers
) {

    private val repoListRateLimit = RateLimiter<Forecast>(10, TimeUnit.MINUTES)
    private val forecastResources = object : NetworkBoundResource<Forecast, NetworkForecast>() {
        override suspend fun saveCallResult(item: NetworkForecast) {
            db.forecastDao().insertForecast(forecastMappers.networkToDb.map(item))
        }

        override fun shouldFetch(data: Forecast?): Boolean {
            return data == null || repoListRateLimit.shouldFetch(data)
        }

        override fun loadFlowFromDb(): Flow<Forecast> {
            return db.forecastDao().getForecastFlow().mapNotNull { forecast ->
                forecast?.let { it ->
                    forecastMappers.dbToDomain.map(it)
                }
            }
        }

        override suspend fun loadFromDb(): Forecast? {
            return db.forecastDao().getForecast()?.let { it ->
                forecastMappers.dbToDomain.map(it)
            }
        }

        override suspend fun createCall(): Response<NetworkForecast> {
            return service.getForecast()
        }

    }

    private val callApi = object : NetworkCall<NetworkForecast>() {
        override suspend fun createCall(): Response<NetworkForecast> {
            return service.getForecast()
        }
    }

    fun loadForecast(): Flow<Resource<Forecast>> {
        return forecastResources.loadData()
    }

    fun refreshForecast(): Flow<Resource<Forecast>> {
        return forecastResources.refresh()
    }

    fun callApi(): Flow<Resource<NetworkForecast>> {
        return callApi.execute()
    }

}