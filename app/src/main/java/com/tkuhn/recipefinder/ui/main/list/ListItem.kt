package com.tkuhn.recipefinder.ui.main.list

data class ListItem(
    val label: String,
    val onClick: () -> Unit
)