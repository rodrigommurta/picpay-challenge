package com.picpay.desafio.android.domain.model.contacts

import com.picpay.desafio.android.utils.State
import com.picpay.desafio.android.utils.StateBearer
import java.io.Serializable

data class ContactsScreen(
    override val state: State<Any> = State.Loading(),
    val contacts: List<Contact>? = null
) : Serializable, StateBearer

data class Contact(
    val id: Int,
    val name: String,
    val username: String,
    val image: String
) : Serializable