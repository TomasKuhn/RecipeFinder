package com.tkuhn.recipefinder.datasource.network.dto

import com.google.gson.annotations.SerializedName


data class NetworkForecast(
    @SerializedName("cod") val cod: String
)