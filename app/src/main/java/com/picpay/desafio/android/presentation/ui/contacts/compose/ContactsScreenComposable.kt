package com.picpay.desafio.android.presentation.ui.contacts.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.contacts.ContactsScreen
import com.picpay.desafio.android.presentation.ui.contacts.ContactsListener
import com.picpay.desafio.android.presentation.ui.contacts.DummyContactsListener
import com.picpay.desafio.android.presentation.ui.contacts.compose.fakedata.ContactsScreenFakeData
import com.picpay.desafio.android.presentation.ui.utils.compose.ErrorFeedbackComposable
import com.picpay.desafio.android.utils.State

const val ERROR_TAG = "error"
const val LOADING_TAG = "loading"
const val SUCCESS_TAG = "success"
const val LOADING_IMAGE_TAG = "loading_image"
const val CACHE_TAG = "cache_tag"

@Composable
fun ContactsScreenComposable(
    modifier: Modifier = Modifier,
    screen: ContactsScreen,
    listener: ContactsListener,
) {
    Scaffold(
        containerColor = colorResource(R.color.colorPrimaryDark)
    ) { paddingValues ->
        val errorWithoutCache = screen.state.isError && screen.contacts.isNullOrEmpty()

        if (errorWithoutCache) {
            val state = screen.state as State.Error
            ErrorFeedbackComposable(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(ERROR_TAG),
                errorMessage = state.error.message.orEmpty(),
                listener = listener,
            )
        } else if (screen.state is State.Loading) {
            Column(
                modifier = modifier
                    .padding(paddingValues)
                    .testTag(LOADING_TAG)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = stringResource(R.string.title),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.weight(0.75f))

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                        .testTag(LOADING_IMAGE_TAG),
                    color = colorResource(R.color.colorAccent)
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        } else {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .testTag(SUCCESS_TAG)
            ) {
                (screen.state as? State.Error)?.let { errorWithCache ->
                    CacheAlertComposable(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .testTag(CACHE_TAG),
                        errorWithCache.error
                    )
                }

                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = stringResource(R.string.title),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                screen.contacts?.let { contacts ->
                    contacts.forEach { contact ->
                        ContactComposable(
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .fillMaxWidth(),
                            contact = contact,
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview("ContactsScreenPreview")
private fun ContactsScreenPreview(
    @PreviewParameter(ContactsScreenFakeData::class) data: ContactsScreen
) {
    ContactsScreenComposable(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        screen = data,
        listener = DummyContactsListener,
    )
}