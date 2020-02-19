package com.tkuhn.recipefinder.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkRecipe(
    @SerializedName("id") val id: Long,
    @SerializedName("calories") val calories: Int,
    @SerializedName("carbs") val carbs: String,
    @SerializedName("fat") val fat: String,
    @SerializedName("image") val image: String,
    @SerializedName("imageType") val imageType: String,
    @SerializedName("protein") val protein: String,
    @SerializedName("title") val title: String
)