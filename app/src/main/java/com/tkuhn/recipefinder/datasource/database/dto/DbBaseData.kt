package com.tkuhn.recipefinder.datasource.database.dto

import androidx.room.ColumnInfo

open class DbBaseData(
    @ColumnInfo(name = "validity") var validityInSeconds: Long
) {
    @ColumnInfo(name = "timestamp")
    var timestamp = System.currentTimeMillis()

    fun isValid(): Boolean {
        val now = System.currentTimeMillis()
        return now < timestamp + validityInSeconds * 1000
    }
}