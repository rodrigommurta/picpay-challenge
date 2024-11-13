package com.picpay.desafio.android.data.service.contacts

import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ContactsServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ContactsService

    @Before
    fun createService() {
        val factory = GsonBuilder().create()

        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(factory))
            .build()
            .create(ContactsService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `SHOULD hit right path with expected endpoint`() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result = service.getContacts("users")
            val request = mockWebServer.takeRequest()

            assertEquals(request.path, "/users")
        }
    }
}