package com.practice.contactapp.providerfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.contactapp.repository.ContactRepo
import com.practice.contactapp.viewmodels.ContactViewModel

class ContactVMProviderFactory (
    private val application: Application,
    private val repo: ContactRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(application,repo) as T
    }
}