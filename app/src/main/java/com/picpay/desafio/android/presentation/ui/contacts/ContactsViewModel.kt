package com.picpay.desafio.android.presentation.ui.contacts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.contacts.Contact
import com.picpay.desafio.android.domain.model.contacts.ContactsScreen
import com.picpay.desafio.android.domain.usecase.contacts.GetContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val CONTACTS_STATE = "CONTACTS_STATE"
const val CONTACTS_PARAM = "users"

class ContactsViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val savedState: SavedStateHandle,
) : ViewModel(), ContactsListener {
    private val _screen = MutableStateFlow(ContactsScreen())
    val screen: StateFlow<ContactsScreen> = _screen

    init {
        getUsers()
        if (savedState.contains(CONTACTS_STATE).not()) {
            getUsers()
        }
        savedState.get<List<Contact>>(CONTACTS_STATE)
    }

    private fun getUsers() {
        viewModelScope.launch {
            getContactsUseCase(
                CONTACTS_PARAM,
                ContactsScreen()
            )
                .onEach {
                    savedState[CONTACTS_STATE] = it.contacts
                }
                .collect {
                    _screen.emit(it)
                }
        }
    }

    override fun onButtonClicked() {
        getUsers()
    }
}