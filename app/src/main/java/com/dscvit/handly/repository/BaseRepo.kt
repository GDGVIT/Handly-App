package com.dscvit.handly.repository

import androidx.lifecycle.liveData
import com.dscvit.handly.model.Result

open class BaseRepo {

    protected fun <T> makeRequest(request: suspend () -> Result<T>) = liveData {
        emit(Result.Loading<T>())

        when (val response = request()) {
            is Result.Success -> {
                emit(Result.Success(response.data))
            }
            is Result.Error -> {
                emit(Result.Error(response.message))
            }
            else -> {
            }
        }
    }

//    protected fun <T, A> makeRequestAndSave(
//        databaseQuery: () -> LiveData<T>,
//        networkCall: suspend () -> Result<A>,
//        saveCallResult: suspend (A) -> Unit
//    ): LiveData<Result<T>> = liveData(Dispatchers.IO) {
//        emit(Result.Loading<T>())
//
//        val source = databaseQuery.invoke().map { Result.Success(it) }
//        emitSource(source)
//
//        val response = networkCall.invoke()
//        when (response) {
//            is Result.Success -> {
//                saveCallResult(response.data!!)
//            }
//            is Result.Error -> {
//                emit(Result.Error(response.message!!))
//                emitSource(source)
//            }
//            else -> {
//            }
//        }
//    }
}