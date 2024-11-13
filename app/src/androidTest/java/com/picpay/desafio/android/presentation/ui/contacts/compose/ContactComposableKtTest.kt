package com.picpay.desafio.android.presentation.ui.contacts.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.picpay.desafio.android.domain.model.contacts.Contact
import org.junit.Rule
import org.junit.Test

class ContactComposableKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contactComposableTest() {
        composeTestRule.setContent {
            ContactComposable(
                modifier = Modifier,
                contact = Contact(
                    id = 1,
                    name = "Rodrigo Murta",
                    username = "rodrigo.mmurta",
                    image = "https://randomuser.me/api/portraits/men/1.jpg"
                )
            )
        }

        composeTestRule.onNodeWithTag(IMAGE_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(USERNAME_TAG)
            .assertIsDisplayed()
            .assertTextEquals("rodrigo.mmurta")

        composeTestRule.onNodeWithTag(NAME_TAG)
            .assertIsDisplayed()
            .assertTextEquals("Rodrigo Murta")

    }
}