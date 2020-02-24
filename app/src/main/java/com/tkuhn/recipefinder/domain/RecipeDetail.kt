package com.tkuhn.recipefinder.domain

import com.tkuhn.recipefinder.utils.Identifiable

data class RecipeDetail(
    override val id: Long,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val sourceUrl: String,
    val likes: Int,
    val healthScore: Float,
    val score: Float,
    val ingredients: List<String>,
    val isValid: Boolean
) : Identifiable