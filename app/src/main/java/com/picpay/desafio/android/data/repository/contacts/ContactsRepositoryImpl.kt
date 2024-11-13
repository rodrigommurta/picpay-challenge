package com.picpay.desafio.android.data.repository.contacts

import com.picpay.desafio.android.data.local.dao.ContactDao
import com.picpay.desafio.android.data.local.entities.database.toDomain
import com.picpay.desafio.android.data.model.contacts.toDb
import com.picpay.desafio.android.data.repository.utils.networkAdapter
import com.picpay.desafio.android.data.service.contacts.ContactsService
import com.picpay.desafio.android.domain.model.contacts.Contact
import com.picpay.desafio.android.domain.repository.contacts.ContactsRepository
import com.picpay.desafio.android.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsRepositoryImpl(
    private val service: ContactsService,
    private val dao: ContactDao,
) : ContactsRepository {
    override suspend fun getContacts(endpoint: String): Flow<State<List<Contact>>> =
        networkAdapter(
            query = { dao.queryContacts().map { it.toDomain() } },
            get = { service.getContacts(endpoint) },
            saveGetResult = { listContactDb ->
                dao.deleteAll()
                dao.insertAll(listContactDb.toDb())
            },
        )
}

