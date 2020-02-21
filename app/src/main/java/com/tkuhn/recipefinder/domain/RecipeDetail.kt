package com.tkuhn.recipefinder.domain

import com.tkuhn.recipefinder.utils.Identifiable

data class RecipeDetail(
    override val id: Long,
    val title: String,
    val isValid: Boolean
) : Identifiable