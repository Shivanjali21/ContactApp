package com.practice.contactapp.fragments.update

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.practice.contactapp.R
import com.practice.contactapp.activities.MainActivity
import com.practice.contactapp.databinding.FragmentUpdateBinding
import com.practice.contactapp.db.entity.ContactData
import com.practice.contactapp.viewmodels.ContactViewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {

    private var binding: FragmentUpdateBinding? = null
    private lateinit var contactVM: ContactViewModel
    private lateinit var currentContact : ContactData
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)

        contactVM = (activity as MainActivity).contactViewModel
        currentContact = args.sendData!!
        binding!!.apply {
            val imageByte = currentContact.profile
            if (imageByte != null) {
                val image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                ivContactLogoUpdate.setImageBitmap(image)
            }
           etUserNameUpdate.setText(currentContact.name)
           etUserMobileUpdate.setText(currentContact.mobile)
           etUserEmailIdUpdate.setText(currentContact.email)

            mBtnUpdate.setOnClickListener {
                val contactName = etUserNameUpdate.text.toString()
                val contactMobile = etUserMobileUpdate.text.toString()
                val contactEmail = etUserEmailIdUpdate.text.toString()
                if(contactName.isNotEmpty() && contactMobile.isNotEmpty() && contactEmail.isNotEmpty()) {
                    val contact = ContactData(currentContact.id, imageByte, contactName, contactMobile, contactEmail)
                    contactVM.updateContact(contact)
                    Snackbar.make(view, "Contact Updated successfully", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateFragment_to_allContactFragment)
                }else {
                    Toast.makeText(requireContext(), "Please All Fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure want to delete this note?")
            setPositiveButton("DELETE"){_,_ ->
                contactVM.deleteContact(currentContact)
                findNavController().navigate(
                    R.id.action_updateFragment_to_allContactFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
}