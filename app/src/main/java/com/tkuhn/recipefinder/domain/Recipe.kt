package com.tkuhn.recipefinder.domain

import com.tkuhn.recipefinder.utils.Identifiable

data class Recipe(
    override val id: Long,
    val title: String
) : Identifiable