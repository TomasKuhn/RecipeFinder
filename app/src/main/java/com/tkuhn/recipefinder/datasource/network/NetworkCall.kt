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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class NetworkCall<ResponseData> {

    fun execute(): Flow<Resource<ResponseData>> {
        return flow<Resource<ResponseData>> {
            emit(Resource.Loading())
            val response = createCall()
            if (response.isSuccessful) {
                val responseData = processResponse(response)
                emit(Resource.Success(responseData))
            } else {
                onFailed()
                emit(Resource.Error(ResourceError.HttpError(response.message(), response.code())))
            }
        }.catch {
            emit(Resource.Error(ResourceError.networkError(it)))
        }.flowOn(Dispatchers.IO)
    }

    protected open fun onFailed() {}

    protected open fun processResponse(response: Response<ResponseData>) = response.body()

    protected abstract suspend fun createCall(): Response<ResponseData>
}
