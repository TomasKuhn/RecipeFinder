package com.tkuhn.recipefinder.utils

import com.google.gson.GsonBuilder

object Gson {

    val instance = GsonBuilder()
        .enableComplexMapKeySerialization()
        .create()
}