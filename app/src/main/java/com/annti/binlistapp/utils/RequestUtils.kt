package com.annti.binlistapp.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object RequestUtils {

    fun <T> requestFlow(requestFunc: suspend  () -> T): Flow<Request<T>> {
        return flow<Request<T>>{
            emit(Request.Success(requestFunc()))
        }.onStart {
            emit(Request.Loading())
        }.catch { error ->
            emit(
                when(error){
                    is UnknownHostException -> Request.Error(AppError.Code.NETWORK_CONNECTION, "Нет подключения к интернету")
                    is SocketTimeoutException -> Request.Error(AppError.Code.TIMEOUT, "Превышено время ожидания ответа")
                    is HttpException -> {
                        when (error.code()){
                            404 -> Request.Error(AppError.Code.SERVER_ERROR, "Ресурс не найден")
                            else -> {
                                Request.Error(AppError.Code.SERVER_ERROR, "Сетевая ошибка: $error")
                            }
                        }
                    }
                    else -> Request.Error(AppError.Code.SERVER_ERROR, "Неизвестная ошибка: $error")
                }
            )
        }
    }
}
