package com.tkuhn.recipefinder.datasource.network

import timber.log.Timber
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException

sealed class ResourceError(
    open val message: String
) {

    data class HttpError(override val message: String, val code: Int) : ResourceError(message)
    data class NoInternetError(override val message: String) : ResourceError(message)
    data class TimeoutError(override val message: String) : ResourceError(message)
    data class IoError(override val message: String) : ResourceError(message)
    data class UnexpectedError(override val message: String) : ResourceError(message)

    companion object {
        fun networkError(exception: Throwable?): ResourceError {
            Timber.w(exception)
            return when (exception) {
                is NoInternetException    -> NoInternetError("No internet connection")
                is SocketTimeoutException,
                is InterruptedIOException -> TimeoutError("Timeout error")
                is IOException            -> IoError("Communication error")
                else                      -> UnexpectedError("Something went wrong")
            }
        }
    }
}