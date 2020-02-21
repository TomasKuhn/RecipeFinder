package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.extensions.toText
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
                is NoInternetException    -> NoInternetError(R.string.err_no_internet.toText())
                is SocketTimeoutException,
                is InterruptedIOException -> TimeoutError(R.string.err_timeout.toText())
                is IOException            -> IoError(R.string.err_io.toText())
                else                      -> UnexpectedError(R.string.err_something_went_wrong.toText())
            }
        }
    }
}