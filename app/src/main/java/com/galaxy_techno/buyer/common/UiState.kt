package com.galaxy_techno.buyer.common

sealed class UiState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : UiState<T>(data)
    class Fail<T>(data: T) : UiState<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : UiState<T>(data, message)
    class Loading<T>(data: T? = null) : UiState<T>(data)
    class Empty<T>(data: T? = null) : UiState<T>(data)
}