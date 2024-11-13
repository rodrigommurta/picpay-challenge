package com.picpay.desafio.android.data.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.local.database.ContactDatabase
import com.picpay.desafio.android.data.repository.contacts.ContactsRepositoryImpl
import com.picpay.desafio.android.data.service.contacts.ContactsService
import com.picpay.desafio.android.domain.repository.contacts.ContactsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
private const val OK_HTTP = "Ok Http"

object DataModule {
    fun load() = loadKoinModules(
        listOf(
            repositoryModule,
            serviceModule,
            networkModule,
            daoModule,
        )
    )

    private val repositoryModule = module {
        single<ContactsRepository> {
            ContactsRepositoryImpl(
                service = get(),
                dao = get()
            )
        }
    }

    private val serviceModule = module {
        single<ContactsService> { createService(get(), get()) }
    }

    private val networkModule = module {
        single { createOkHttpClient() }

        single { GsonBuilder().create() }
    }

    private val daoModule = module {
        single { ContactDatabase.getInstance(androidContext()).dao }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        converterFactory: Gson,
    ): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                converterFactory
            )
        ).build()
        .create(T::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor {
            Log.i(OK_HTTP, it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}