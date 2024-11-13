package com.picpay.desafio.android.data.service.contacts

import com.picpay.desafio.android.data.model.contacts.ContactRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsService {
    @GET("{endpoint}")
    suspend fun getContacts(
        @Path("endpoint")
        endpoint: String
    ): List<ContactRemote>
}