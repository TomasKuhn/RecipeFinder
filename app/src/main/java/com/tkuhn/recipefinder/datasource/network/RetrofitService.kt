package com.tkuhn.recipefinder.datasource.network

import com.tkuhn.recipefinder.App
import com.tkuhn.recipefinder.R
import com.tkuhn.recipefinder.utils.hasNetworkConnection
import com.tkuhn.recipefinder.utils.toText
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(R.string.api_url.toText())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(NoNetworkInterceptor())
                .addInterceptor(ApiInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .build()

    inline fun <reified T> createApiService(): T {
        return retrofit.create(T::class.java)
    }

    private class NoNetworkInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            if (!App.instance.hasNetworkConnection()) {
                throw NoInternetException("${chain.request().url.toUrl()}")
            } else {
                return chain.proceed(chain.request())
            }
        }
    }

    private class ApiInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url =
                request.url.newBuilder().addQueryParameter("appid", R.string.api_key.toText())
                    .build()
            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }
}