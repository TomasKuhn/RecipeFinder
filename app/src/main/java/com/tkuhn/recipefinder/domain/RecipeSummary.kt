package com.tkuhn.recipefinder.domain

data class RecipeSummary(
    val title: String,
    val summary: String,
    val isValid: Boolean
)