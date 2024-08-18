package com.sborges.price.data.retrofit

sealed class ResponseWrapper<out T> {
    data class Success<out T>(val data: T) : ResponseWrapper<T>()
    data class Error(
        val message: String,
        val code: Int? = null
    ) : ResponseWrapper<Nothing>()
}
