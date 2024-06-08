package com.practice.contactapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.practice.contactapp.db.entity.ContactData
import com.practice.contactapp.repository.ContactRepo
import kotlinx.coroutines.launch

class ContactViewModel (application: Application, private var contactRepo : ContactRepo): AndroidViewModel(application) {

    fun createContact(contactData: ContactData) = viewModelScope.launch {
       contactRepo.createContact(contactData)
    }

    fun updateContact(contactData: ContactData) = viewModelScope.launch {
       contactRepo.updateContact(contactData)
    }

    fun deleteContact(contactData: ContactData) = viewModelScope.launch {
       contactRepo.deleteContact(contactData)
    }

    fun getAllContact() = contactRepo.readContact()

}