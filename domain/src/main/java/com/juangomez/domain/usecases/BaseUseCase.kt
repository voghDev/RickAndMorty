package com.juangomez.domain.usecases

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): CEither<Failure, Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (CEither<Failure, Type>) -> Unit = {}
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