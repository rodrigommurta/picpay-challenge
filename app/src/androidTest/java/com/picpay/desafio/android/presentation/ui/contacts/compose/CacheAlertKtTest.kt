package com.picpay.desafio.android.presentation.ui.contacts.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.picpay.desafio.android.utils.ErrorInformation
import org.junit.Rule
import org.junit.Test

class CacheAlertKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cacheAlertComposableTest() {
        composeTestRule.setContent {
            CacheAlertComposable(
                error = ErrorInformation(
                    message = "Ocorreu um erro. Exibindo dados em cache."
                )
            )
        }

        composeTestRule.onNodeWithTag(TEXT_TAG).assertIsDisplayed()
    }
}