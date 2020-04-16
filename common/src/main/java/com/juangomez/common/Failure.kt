package com.juangomez.common

sealed class Failure(val exception: Exception = Exception("Failure")) {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownRemoteError : Failure()
}