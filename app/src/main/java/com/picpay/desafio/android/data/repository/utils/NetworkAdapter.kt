package com.picpay.desafio.android.data.repository.utils

import com.picpay.desafio.android.utils.State
import com.picpay.desafio.android.utils.toErrorInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <Result, Request> networkAdapter(
    crossinline query: () -> Flow<Result>,
    crossinline get: suspend () -> Request,
    crossinline saveGetResult: suspend (Request) -> Unit,
): Flow<State<Result>> = flow {
    var data = query().first()

    try {
        saveGetResult(get())
        data = query().first()

    } catch (error: Exception) {
        emit(
            State.Error(
                data,
                error.toErrorInformation(
                    "Não foi possível conectar ao servidor. Exibindo dados em cache."
                ),
            )
        )
    }
    emit(State.Success(data))
}