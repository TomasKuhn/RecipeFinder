package com.tkuhn.recipefinder.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val id: String,
    val message: String
) : Parcelable