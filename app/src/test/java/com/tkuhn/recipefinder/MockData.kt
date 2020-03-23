package com.tkuhn.recipefinder

import com.tkuhn.recipefinder.domain.Ingredient
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.domain.RecipeDetail
import com.tkuhn.recipefinder.domain.RecipeSummary

object MockData {

    fun createRecipe(
        id: Long = 30078L,
        title: String = "Yuzu Dipping Sauce",
        imageUrl: String = "https://spoonacular.com/recipeImages/30078-312x231.jpg",
        calories: Int = 3,
        carbs: String = "0g",
        fat: String = "0g",
        protein: String = "1g"
    ): Recipe {
        return Recipe(
            id,
            title,
            imageUrl,
            calories,
            carbs,
            fat,
            protein
        )
    }

    fun createRecipeDetail(
        id: Long = 30078L,
        title: String = "Yuzu Dipping Sauce",
        image: String = "https://spoonacular.com/recipeImages/30078-556x370.jpg",
        readyInMinutes: Int = 2,
        sourceUrl: String = "http://www.marthastewart.com/315027/yuzu-dipping-sauce",
        likes: Int = 0,
        healthScore: Float = 0f,
        score: Float = 4f,
        ingredients: List<Ingredient> = emptyList(),
        isValid: Boolean = true
    ): RecipeDetail {
        return RecipeDetail(
            id,
            title,
            image,
            readyInMinutes,
            sourceUrl,
            likes,
            healthScore,
            score,
            ingredients,
            isValid
        )
    }

    fun createRecipeSummary(
        title: String = "Yuzu Dipping Sauce",
        summary: String = "If you have roughly <b>2 minutes</b> to spend in the kitchen...",
        isValid: Boolean = true
    ): RecipeSummary {
        return RecipeSummary(
            title,
            summary,
            isValid
        )
    }
}