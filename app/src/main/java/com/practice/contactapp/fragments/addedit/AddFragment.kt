package com.practice.contactapp.fragments.addedit

import android.app.Activity
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.practice.contactapp.R
import com.practice.contactapp.activities.MainActivity
import com.practice.contactapp.databinding.FragmentAddBinding
import com.practice.contactapp.db.entity.ContactData
import com.practice.contactapp.viewmodels.ContactViewModel

class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private var contact = ContactData()
    private lateinit var contactVM: ContactViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)

        contactVM = (activity as MainActivity).contactViewModel

        binding!!.apply {

            ivContactLogo.setOnLongClickListener {
                val dialog = Dialog(requireActivity())
                dialog.setContentView(R.layout.layout_dialog_image)
                val image = dialog.findViewById<ImageView>(R.id.ivImage)
                val imgObject = binding!!.ivContactLogo.drawable
                image.setImageDrawable(imgObject)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                val lp = WindowManager.LayoutParams()
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    lp.blurBehindRadius = 100
                    lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
                }
                dialog.window?.attributes = lp
                dialog.show()
                true
            }

            ivContactLogo.setOnClickListener {
                ImagePicker.with(requireActivity())
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            }

            mBtnAdd.setOnClickListener {
               val imageByte = contact.profile
                if (imageByte != null) {
                    val image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                    ivContactLogo.setImageBitmap(image)
                }
                binding.apply {
                    val contactName = etUserName.text.toString()
                    val contactMobile = etUserMobile.text.toString()
                    val contactEmail = etUserEmailId.text.toString()
                    if(contactName.isNotEmpty() && contactMobile.isNotEmpty() && contactEmail.isNotEmpty()) {
                        val contact = ContactData(contact.id, imageByte, contactName, contactMobile, contactEmail)
                        contactVM.createContact(contact)
                        Snackbar.make(view, "Contact Saved successfully", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_addFragment_to_allContactFragment)
                    }else {
                        Toast.makeText(requireContext(), "Please All Fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                binding!!.ivContactLogo.setImageURI(fileUri)
                //1. uri se img extract  2.uri to byteArray
                val imgByte = requireContext().contentResolver.openInputStream(fileUri)?.readBytes()
                contact.profile = imgByte
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
