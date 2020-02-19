/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tkuhn.recipefinder.datasource.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType> {

    fun loadData(): Flow<Resource<ResultType>> {
        var dbData: ResultType? = null
        return flow {
            emit(Resource.Loading())
            dbData = loadFromDb()
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
            emitAll(loadFlowFromDb().distinctUntilChanged().map {
                Resource.Success(it)
            })
        }.catch {
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
                    emit(Resource.Success(loadFromDb()))
                } else {
                    emit(Resource.Success())
                }
            } else {
                onFetchFailed()
                emit(Resource.Error(ResourceError.HttpError(response.message(), response.code())))
            }
        }.catch {
            emit(Resource.Error(ResourceError.networkError(it)))
        }.flowOn(Dispatchers.IO)
    }

    protected open fun onFetchFailed() {}

    protected open fun processResponse(response: Response<RequestType>) = response.body()

    protected abstract suspend fun saveCallResult(item: RequestType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFlowFromDb(): Flow<ResultType>

    protected abstract suspend fun loadFromDb(): ResultType?

    protected abstract suspend fun createCall(): Response<RequestType>
}
