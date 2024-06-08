package com.practice.contactapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.practice.contactapp.R
import com.practice.contactapp.databinding.ActivityMainBinding
import com.practice.contactapp.db.ContactDatabase
import com.practice.contactapp.providerfactory.ContactVMProviderFactory
import com.practice.contactapp.repository.ContactRepo
import com.practice.contactapp.viewmodels.ContactViewModel
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    lateinit var contactViewModel : ContactViewModel
    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.mainActRoot)

        val navHost = supportFragmentManager.findFragmentById(R.id.mainFCV) as NavHostFragment
        navController = navHost.navController

        setUpViewModel()
    }
    private fun setUpViewModel(){
        val noteRepository = ContactRepo(ContactDatabase(this))
        val viewModelProviderFactory = ContactVMProviderFactory(application, noteRepository)
        contactViewModel = ViewModelProvider(this, viewModelProviderFactory)[ContactViewModel::class.java]
    }
}