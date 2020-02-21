package com.tkuhn.recipefinder.domain

import android.graphics.Color
import android.text.SpannedString
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.Identifiable
import com.tkuhn.recipefinder.utils.extensions.toText

data class Recipe(
    override val id: Long,
    val title: String,
    val imageUrl: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val protein: String
) : Identifiable {

    fun formattNutriends(): SpannedString {
        return buildSpannedString {
            color(Color.RED) { append(R.string.var_protein.toText(protein)) }
            append(" ")
            color(Color.BLUE) { append(R.string.var_carbs.toText(carbs)) }
            append(" ")
            color(Color.GREEN) { append(R.string.var_fat.toText(fat)) }
        }
    }
}