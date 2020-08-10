package com.juangomez.common

sealed class CEither<out L : Failure, out R> {

    data class Left<out L : Failure>(val failure: L) : CEither<L, Nothing>()

    data class Right<out R>(val data: R) : CEither<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L : Failure> left(failure: L) =
        Left(failure)
    fun <R> right(data: R) = Right(data)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(failure)
            is Right -> fnR(data)
        }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L : Failure, R> CEither<L, R>.flatMap(fn: (R) -> CEither<L, T>): CEither<L, T> =
    when (this) {
        is CEither.Left -> CEither.Left(failure)
        is CEither.Right -> fn(data)
    }

fun <T, L : Failure, R> CEither<L, R>.map(fn: (R) -> (T)): CEither<L, T> = this.flatMap(fn.c(::right))