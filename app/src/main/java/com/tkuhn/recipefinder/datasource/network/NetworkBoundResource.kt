
package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.utils.extensions.takeFirstOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import retrofit2.Response
import timber.log.Timber

abstract class NetworkBoundResource<ResultType, ResponseType> {

    fun loadData(): Flow<Resource<ResultType>> {
        var dbData: ResultType? = null
        return flow {
            emit(Resource.Loading())
            dbData = loadCurrentValueFromDb()
            if (shouldFetch(dbData)) {
                if (dbData != null) {
                    emit(Resource.Loading(dbData))
                }
                val response = createCall()
                if (response.isSuccessful) {
                    val responseData = processResponse(response)
                    if (responseData == null) {
                        emit(Resource.Success())
                    } else {
                        saveCallResult(responseData)
                    }
                } else {
                    onFetchFailed()
                    emit(
                        Resource.Error(
                            ResourceError.HttpError(
                                response.message(),
                                response.code()
                            ), dbData
                        )
                    )
                    return@flow
                }
            }
            emitAll(loadFromDb().distinctUntilChanged().mapNotNull {
                it?.let { Resource.Success(it) }
            })
        }.catch {
            Timber.w(it, "Load data went wrong")
            emit(Resource.Error(ResourceError.networkError(it), dbData))
        }.flowOn(Dispatchers.IO)
    }

    fun refresh(): Flow<Resource<ResultType>> {
        return flow<Resource<ResultType>> {
            emit(Resource.Loading())
            val response = createCall()
            if (response.isSuccessful) {
                val responseData = processResponse(response)
                if (responseData != null) {
                    saveCallResult(responseData)
                    emit(Resource.Success(loadCurrentValueFromDb()))
                } else {
                    emit(Resource.Success())
                }
            } else {
                onFetchFailed()
                emit(Resource.Error(ResourceError.HttpError(response.message(), response.code())))
            }
        }.catch {
            Timber.w(it, "Refresh data went wrong")
            emit(Resource.Error(ResourceError.networkError(it)))
        }.flowOn(Dispatchers.IO)
    }

    protected open fun onFetchFailed() {}

    protected open fun processResponse(response: Response<ResponseType>) = response.body()

    protected abstract suspend fun saveCallResult(item: ResponseType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): Flow<ResultType?>

    protected abstract suspend fun createCall(): Response<ResponseType>

    private suspend fun loadCurrentValueFromDb(): ResultType? {
        return loadFromDb().takeFirstOrNull()
    }
}
