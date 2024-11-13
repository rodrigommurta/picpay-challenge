package com.picpay.desafio.android.di

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(
        listOf(
            configureDataModuleForTest(BASE_URL),
            configureDomainModuleForTest(),
        )
    )
}