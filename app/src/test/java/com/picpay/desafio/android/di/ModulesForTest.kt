package com.picpay.desafio.android.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.local.dao.ContactDao
import com.picpay.desafio.android.data.local.database.ContactDatabase
import com.picpay.desafio.android.data.repository.contacts.ContactsRepositoryImpl
import com.picpay.desafio.android.data.service.contacts.ContactsService
import com.picpay.desafio.android.domain.repository.contacts.ContactsRepository
import com.picpay.desafio.android.domain.usecase.contacts.GetContactsUseCase
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun configureDataModuleForTest(baseUrl: String) = module {
    single<ContactsService> { createServiceForTest(baseUrl = baseUrl) }

    single<ContactsRepository> { ContactsRepositoryImpl(get(), createDaoForTest()) }
}

fun configureDomainModuleForTest() = module {
    factory<GetContactsUseCase> { GetContactsUseCase(get()) }
}

private inline fun <reified T> createServiceForTest(baseUrl: String): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )
        .build()
        .create(T::class.java)
}

fun createDaoForTest(): ContactDao {
    lateinit var contactDatabase: ContactDatabase

    val context = ApplicationProvider.getApplicationContext<Context>()
    contactDatabase = Room.inMemoryDatabaseBuilder(
        context,
        ContactDatabase::class.java
    ).build()

    return contactDatabase.dao
}