package com.tkuhn.recipefinder.datasource.database.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.tkuhn.recipefinder.domain.Ingredient
import com.tkuhn.recipefinder.utils.Gson
import timber.log.Timber

class RecipeDetailDbConverters {

    private val gson = Gson.instance

    @TypeConverter
    fun stringToIngredientList(data: String?): List<Ingredient>? {
        if (data == null) return null
        Timber.d("xxx data are $data")
        return gson.fromJson(data, object : TypeToken<List<Ingredient>>() {}.type)
    }

    @TypeConverter
    fun ingredientListToString(strings: List<Ingredient>?): String {
        return gson.toJson(strings)
    }
}