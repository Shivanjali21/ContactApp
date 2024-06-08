package com.practice.contactapp.fragments.allcontact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.practice.contactapp.R
import com.practice.contactapp.activities.MainActivity
import com.practice.contactapp.databinding.FragmentAllContactBinding
import com.practice.contactapp.viewmodels.ContactViewModel
import kotlinx.coroutines.launch

class AllContactFragment : Fragment(R.layout.fragment_all_contact) {

    private var binding: FragmentAllContactBinding? = null
    private lateinit var allContactVM: ContactViewModel
    private val contactAdapter: AllContactAdapter by lazy {
        AllContactAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllContactBinding.bind(view)

        allContactVM = (activity as MainActivity).contactViewModel

        binding!!.apply {
            rvAllContact.apply {
                adapter = contactAdapter
            }
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_allContactFragment_to_addFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allContactVM.getAllContact().observe(viewLifecycleOwner) { contactsList ->
                    contactAdapter.asyncListDiffer.submitList(contactsList)
                    if (contactsList.isNotEmpty()) {
                        binding!!.apply {
                            rvAllContact.visibility = View.VISIBLE
                            noContact.visibility = View.GONE
                        }
                    } else {
                        binding!!.apply {
                            rvAllContact.visibility = View.GONE
                            noContact.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
} 