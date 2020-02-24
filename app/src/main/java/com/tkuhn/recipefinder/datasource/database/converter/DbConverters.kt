package com.tkuhn.recipefinder.datasource.database.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.tkuhn.recipefinder.utils.Gson

class DbConverters {

    private val gson = Gson.instance

    @TypeConverter
    fun stringToStringList(data: String?): List<String>? {
        return gson.fromJson(data, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun stringListToString(strings: List<String>?): String {
        return gson.toJson(strings)
    }
}