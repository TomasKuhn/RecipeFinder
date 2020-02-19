package com.tkuhn.recipefinder.datasource.network


sealed class Resource<out T> {
    abstract val data: T?

    data class Success<out T>(override val data: T? = null) : Resource<T>()
    data class Error<out T>(val error: ResourceError, override val data: T? = null) : Resource<T>()
    data class Loading<out T>(override val data: T? = null) : Resource<T>()
}