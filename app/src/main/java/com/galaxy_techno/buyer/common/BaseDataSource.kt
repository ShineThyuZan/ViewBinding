package com.galaxy_techno.buyer.common

import retrofit2.Response

open class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): UiState<T> {

        try {
            val response = apiCall()
            if (response.isSuccessful) {

                // alvis operator front form not empty , empty --back condition
                val body = response.body() ?: return UiState.Error("EMPTY BODY")
                return UiState.Success(body)
            }
            return UiState.Error("ERROR CODE ${response.code()} : ${response.message()}")
        } catch (e: Exception) {
            return UiState.Error(e.message ?: e.toString())
        }
    }
}