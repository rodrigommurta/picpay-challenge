package com.picpay.desafio.android.domain.usecase.contacts

import com.picpay.desafio.android.domain.model.contacts.ContactsScreen
import com.picpay.desafio.android.domain.repository.contacts.ContactsRepository
import com.picpay.desafio.android.utils.State
import com.picpay.desafio.android.utils.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class GetContactsUseCase(
    private val repository: ContactsRepository
) : UseCase<String, ContactsScreen>() {
    override suspend fun execute(
        param: String,
        currentState: ContactsScreen
    ): Flow<ContactsScreen> = flow {
        when (val result = repository.getContacts(param).firstOrNull()) {
            is State.Success -> {
                emit(
                    currentState.copy(
                        state = State.Success(data = result),
                        contacts = result.data,
                    )
                )
            }

            is State.Error -> {
                emit(
                    currentState.copy(
                        state = State.Error(data = result, error = result.error),
                        contacts = result.data,
                    )
                )
            }

            else -> {
                emit(currentState.copy(state = State.Loading()))
            }
        }
    }
}