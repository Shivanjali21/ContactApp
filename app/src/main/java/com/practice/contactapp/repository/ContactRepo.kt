package com.practice.contactapp.repository

import com.practice.contactapp.db.ContactDatabase
import com.practice.contactapp.db.entity.ContactData
class ContactRepo(private val database: ContactDatabase) {

    suspend fun createContact(contactData: ContactData) = database.contactDao().createContact(contactData)
    suspend fun updateContact(contactData: ContactData) = database.contactDao().updateContact(contactData)
    suspend fun deleteContact(contactData: ContactData) = database.contactDao().deleteContact(contactData)
    fun readContact() = database.contactDao().readContact()
}