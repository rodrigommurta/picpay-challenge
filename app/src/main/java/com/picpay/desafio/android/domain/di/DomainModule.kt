package com.picpay.desafio.android.domain.di

import com.picpay.desafio.android.domain.usecase.contacts.GetContactsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DomainModule {
    fun load() = loadKoinModules(useCaseModule)

    private val useCaseModule = module {
        factory { GetContactsUseCase(get()) }
    }
}