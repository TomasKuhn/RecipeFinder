package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.datasource.network.dto.NetworkForecast
import retrofit2.Response
import retrofit2.http.GET


interface SpotifyService {

    @GET("/data/2.5/forecast?q=Prague")
    suspend fun getForecast(): Response<NetworkForecast>
}