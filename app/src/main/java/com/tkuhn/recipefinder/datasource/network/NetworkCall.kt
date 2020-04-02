package com.tkuhn.recipefinder.datasource.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber

abstract class NetworkCall<ResponseType, ResultType> {

    fun execute(): Flow<Resource<ResultType>> {
        return flow<Resource<ResultType>> {
            emit(Resource.Loading())
            val response = createCall()
            if (response.isSuccessful) {
                val responseData = processResponse(response)
                val result = responseData?.let { convertResponse(it) }
                emit(Resource.Success(result))
            } else {
                onFailed()
                emit(Resource.Error(ResourceError.HttpError(response.message(), response.code())))
            }
        }.catch {
            Timber.w(it, "Network call went wrong")
            emit(Resource.Error(ResourceError.networkError(it)))
        }.flowOn(Dispatchers.IO)
    }

    protected open fun onFailed() {}

    protected open fun processResponse(response: Response<ResponseType>) = response.body()

    protected open fun convertResponse(response: ResponseType): ResultType? = null

    protected abstract suspend fun createCall(): Response<ResponseType>
}
