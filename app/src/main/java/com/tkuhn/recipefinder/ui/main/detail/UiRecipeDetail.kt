package com.tkuhn.recipefinder.ui.main.detail

import com.tkuhn.recipefinder.domain.RecipeDetail


data class UiRecipeDetail(
    val title: String,
    val imageUrl: String,
    val likes: String,
    val score: String,
    val duration: String
) {

    companion object {
        fun create(recipeDetail: RecipeDetail): UiRecipeDetail {
            return UiRecipeDetail(
                title = recipeDetail.title,
                imageUrl = recipeDetail.image,
                likes = recipeDetail.likes.toString(),
                score = String.format("%.0f", recipeDetail.score),
                duration = "${recipeDetail.readyInMinutes} m"
            )
        }
    }
}