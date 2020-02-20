package com.tkuhn.recipefinder.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkRecipeDetail(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("imageType") val imageType: String,
    @SerializedName("readyInMinutes") val readyInMinutes: Int,
    @SerializedName("sourceUrl") val sourceUrl: String,
    @SerializedName("aggregateLikes") val aggregateLikes: Int,
    @SerializedName("healthScore") val healthScore: Float,
    @SerializedName("spoonacularScore") val score: Float,
    @SerializedName("extendedIngredients") val ingredients: List<NetworkIngredient>
)