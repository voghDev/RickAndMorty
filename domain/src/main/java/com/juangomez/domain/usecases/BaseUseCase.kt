package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.common.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch {
            supervisorScope {
                val backgroundJob = async { run(params) }
                onResult(backgroundJob.await())
            }
        }
    }

    class None
}