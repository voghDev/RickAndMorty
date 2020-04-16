package com.juangomez.common

sealed class Either<out L : Failure, out R> {

    data class Left<out L : Failure>(val failure: L) : Either<L, Nothing>()

    data class Right<out R>(val data: R) : Either<Nothing, R>()

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

fun <T, L : Failure, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(failure)
        is Either.Right -> fn(data)
    }

fun <T, L : Failure, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))