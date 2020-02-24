package com.tkuhn.recipefinder.ui.main.search

import android.graphics.Color
import android.text.Spanned
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.domain.Recipe
import com.tkuhn.recipefinder.utils.Identifiable
import com.tkuhn.recipefinder.utils.extensions.toText

data class UiRecipe(
    override val id: Long,
    val title: String,
    val imageUrl: String,
    val calories: String,
    val nutrients: Spanned
) : Identifiable {

    companion object {
        fun create(recipe: Recipe): UiRecipe {
            return UiRecipe(
                id = recipe.id,
                title = recipe.title,
                imageUrl = recipe.imageUrl,
                calories = R.string.var_calories.toText(recipe.calories),
                nutrients = buildSpannedString {
                    color(Color.RED) { append(R.string.var_protein.toText(recipe.protein)) }
                    append(" ")
                    color(Color.BLUE) { append(R.string.var_carbs.toText(recipe.carbs)) }
                    append(" ")
                    color(Color.GREEN) { append(R.string.var_fat.toText(recipe.fat)) }
                }
            )
        }
    }
}