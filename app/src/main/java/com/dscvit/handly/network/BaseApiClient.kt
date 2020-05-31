package com.dscvit.handly.network

import com.dscvit.handly.BuildConfig
import retrofit2.Response
import com.dscvit.handly.model.Result

open class BaseApiClient {

    protected suspend fun <T> getResult(request: suspend () -> Response<T>): Result<T> {
        try {
            val response = request()
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Server response error")
                }
            } else {
                Result.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            val errorMessage = e.message ?: e.toString()
            return if (BuildConfig.DEBUG) {
                Result.Error("Network called failed with message $errorMessage")
            } else {
                Result.Error("Check your internet connection!")
            }
        }
    }
}