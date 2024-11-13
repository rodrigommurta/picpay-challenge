package com.picpay.desafio.android.data.local.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.domain.model.contacts.Contact

@Entity(tableName = "contact")
data class ContactDb(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val image: String
)

fun ContactDb.toDomain() = Contact(
    id = id,
    name = name,
    username = username,
    image = image
)

fun List<ContactDb>.toDomain() = this.map {
    it.toDomain()
}