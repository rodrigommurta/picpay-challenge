package com.picpay.desafio.android.domain.usecase.contacts

import com.picpay.desafio.android.di.configureTestAppComponent
import com.picpay.desafio.android.domain.model.contacts.ContactsScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

const val CONTACTS_PARAM = "users"

class GetContactsUseCaseTest : KoinTest {

    val getContactsUseCase: GetContactsUseCase by inject()

    @Before
    fun setup() {
        configureTestAppComponent()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `WHEN request service, SHOULD return non null or empty result AND state is success`() {
        runBlocking {
            val result = getContactsUseCase(
                CONTACTS_PARAM,
                ContactsScreen()
            )

            println(result.first().contacts?.size)

            assertFalse(result.first().contacts?.isEmpty() ?: true)
            assertNotNull(result)
            assertTrue(result.first().state.isSuccess)
        }
    }

    @Test
    fun `WHEN wrong request service, SHOULD return null result AND state is error`() {
        runBlocking {
            val result = getContactsUseCase(
                CONTACTS_PARAM.plus("s"),
                ContactsScreen()
            )

            println(result.first().contacts?.size)

            assertNull(result)
            assertTrue(result.first().state.isError)
        }
    }
}