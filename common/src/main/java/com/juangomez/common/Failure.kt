package com.juangomez.common

sealed class Failure(val exception: Exception = Exception("Failure")) {
    
}