package com.tkuhn.recipefinder.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tkuhn.recipefinder.datasource.database.Db


abstract class BaseCacheData<T>(database: Db) {

    val data: LiveData<T?>

    init {
        data = liveData {
            emit(load(database))
        }
    }

    abstract suspend fun load(database: Db): T?
}