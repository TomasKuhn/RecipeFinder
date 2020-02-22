package com.tkuhn.recipefinder.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkRecipeSummary(
    @SerializedName("id") val id: Long,
    @SerializedName("summary") val summary: String,
    @SerializedName("title") val title: String
)