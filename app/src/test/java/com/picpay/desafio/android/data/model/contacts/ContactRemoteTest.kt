package com.picpay.desafio.android.data.model.contacts

import com.picpay.desafio.android.domain.model.contacts.Contact
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ContactRemoteTest {
    private val contactRemote = ContactRemote(
        id = 1,
        name = "Sandrine Spinka",
        image = "https://randomuser.me/api/portraits/men/1.jpg",
        username = "Tod86"
    )

    @Test
    fun `SHOULD convert to domain Contact`() {
        val contact: Contact = contactRemote.toDomain()

        assertEquals(contact.username, contactRemote.username)
    }
}