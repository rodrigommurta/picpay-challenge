package com.picpay.desafio.android.domain.repository.contacts

import com.picpay.desafio.android.domain.model.contacts.Contact
import com.picpay.desafio.android.utils.State
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    suspend fun getContacts(endpoint: String): Flow<State<List<Contact>>>
}