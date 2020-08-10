package com.juangomez.remote.services

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

open class APIService<T> constructor(
    serviceClass: Class<T>,
    private val baseURL: String,
    private val converterFactory: Converter.Factory,
    private val interceptors: Array<Interceptor>
) {
    var service: T

    init {
        service = initApiService().create(serviceClass)
    }

    private fun initApiService(): Retrofit {
        val client = OkHttpClient.Builder()

        interceptors.map { client.addInterceptor(it) }

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(converterFactory)
            .client(client.build())
            .build()
    }

    suspend fun <R> execute(apiCall: suspend () -> R): CEither<Failure, R> =
        try {
            CEither.Right(apiCall.invoke())
        } catch (throwable: Throwable) {
            manageAPIError(throwable)
        }

    private fun <R> manageAPIError(throwable: Throwable): CEither<Failure, R> =
        when (throwable) {
            is IOException -> CEither.Left(Failure.NetworkConnection)
            is HttpException -> CEither.Left(Failure.ServerError)
            else -> CEither.Left(Failure.UnknownRemoteError)
        }
}