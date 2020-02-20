package com.tkuhn.recipefinder.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkIngredient(
    @SerializedName("id") val id: Long,
    @SerializedName("image") val image: String,
    @SerializedName("original") val original: String
)