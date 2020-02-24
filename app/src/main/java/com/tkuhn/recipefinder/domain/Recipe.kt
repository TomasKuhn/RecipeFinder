package com.tkuhn.recipefinder.domain

data class Recipe(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val protein: String
)