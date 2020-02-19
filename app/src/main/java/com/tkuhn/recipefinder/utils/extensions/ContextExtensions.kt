package com.tkuhn.recipefinder.utils.extensions

import android.content.Context
import android.net.ConnectivityManager

inline fun Context.hasNetworkConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnected ?: false
}

inline fun Context.hasWifiConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.let { it.type == ConnectivityManager.TYPE_WIFI } ?: false
}