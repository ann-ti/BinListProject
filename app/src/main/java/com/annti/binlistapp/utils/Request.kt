package com.annti.binlistapp.utils

import com.annti.binlistapp.utils.AppError.Code

sealed class Request<T> {
    class Loading<T> : Request<T>()
    data class Success<T>(internal val data: T): Request<T>()
    data class Error<T>(internal val code: Code, internal val message: String): Request<T>()
}