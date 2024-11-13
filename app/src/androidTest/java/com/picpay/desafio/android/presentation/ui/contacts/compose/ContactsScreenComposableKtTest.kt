package com.picpay.desafio.android.presentation.ui.contacts.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.picpay.desafio.android.domain.model.contacts.Contact
import com.picpay.desafio.android.domain.model.contacts.ContactsScreen
import com.picpay.desafio.android.presentation.ui.contacts.DummyContactsListener
import com.picpay.desafio.android.presentation.ui.utils.compose.ERROR_BUTTON_TAG
import com.picpay.desafio.android.presentation.ui.utils.compose.ERROR_TEXT_TAG
import com.picpay.desafio.android.utils.ErrorInformation
import com.picpay.desafio.android.utils.State
import org.junit.Rule
import org.junit.Test

class ContactsScreenComposableKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val contact = Contact(
        id = 1,
        name = "Rodrigo Murta",
        username = "rodrigo.mmurta",
        image = "https://randomuser.me/api/portraits/men/1.jpg"
    )

    private val contactsScreen = ContactsScreen(
        state = State.Success(
            data = listOf(
                contact,
                contact,
                contact,
            )
        ),
        contacts = listOf(
            contact,
            contact,
            contact,
        )
    )

    @Test
    fun contactScreenSuccessComposableTest() {
        composeTestRule.setContent {
            ContactsScreenComposable(
                modifier = Modifier,
                screen = contactsScreen,
                listener = DummyContactsListener,
            )
        }

        composeTestRule.onNodeWithTag(SUCCESS_TAG)
            .assertIsDisplayed()

        composeTestRule.onAllNodesWithTag(IMAGE_TAG)
            .assertCountEquals(3)

        composeTestRule.onAllNodesWithTag(USERNAME_TAG)
            .assertAny(hasTextExactly("rodrigo.mmurta"))

        composeTestRule.onAllNodesWithTag(NAME_TAG)
            .assertAny(hasTextExactly("Rodrigo Murta"))
    }

    @Test
    fun contactScreenLoadingComposableTest() {
        composeTestRule.setContent {
            ContactsScreenComposable(
                modifier = Modifier,
                screen = contactsScreen.copy(state = State.Loading()),
                listener = DummyContactsListener,
            )
        }

        composeTestRule.onNodeWithTag(LOADING_TAG)
            .assertIsDisplayed()

        composeTestRule.onAllNodes(hasTestTag(IMAGE_TAG).not())

        composeTestRule.onNodeWithTag(LOADING_IMAGE_TAG).assertIsDisplayed()
    }

    @Test
    fun contactScreenErrorComposableTest() {
        composeTestRule.setContent {
            ContactsScreenComposable(
                modifier = Modifier,
                screen = ContactsScreen(
                    state = State.Error(
                        error = ErrorInformation(
                            message = "Erro"
                        )
                    )
                ),
                listener = DummyContactsListener,
            )
        }

        composeTestRule.onNodeWithTag(ERROR_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(ERROR_TEXT_TAG)
            .assertIsDisplayed()
            .assertTextEquals("Erro")

        composeTestRule.onNodeWithTag(ERROR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onAllNodes(hasTestTag(IMAGE_TAG).not())

        composeTestRule.onNodeWithTag(LOADING_IMAGE_TAG).assertIsNotDisplayed()
    }
}