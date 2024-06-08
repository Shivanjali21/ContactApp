package com.practice.contactapp.fragments.allcontact

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.contactapp.databinding.RvContactItemBinding
import com.practice.contactapp.db.entity.ContactData

class AllContactAdapter : RecyclerView.Adapter<AllContactAdapter.AllContactVH>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ContactData>() {

        override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem == newItem
        }
    }

    val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllContactVH {
        val binding = RvContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllContactVH(binding)
    }

    override fun onBindViewHolder(holder: AllContactVH, position: Int) {
        val contact = asyncListDiffer.currentList[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener { mView ->
           val directions = AllContactFragmentDirections.actionAllContactFragmentToUpdateFragment(contact)
           mView.findNavController().navigate(directions)
        }
    }
    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
    inner class AllContactVH(private val binding: RvContactItemBinding) : RecyclerView.ViewHolder(binding.mcvContactRoot) {
        fun bind(contact: ContactData) {
            binding.apply {
                if (contact.profile != null) {
                    val imgByte = contact.profile
                    if (imgByte != null) {
                        val img = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.size)
                        ivContactIcon.setImageBitmap(img)
                        ivContactIcon.visibility = View.VISIBLE
                        mtvNameSign.visibility = View.GONE
                    } else {
                        val splitName = contact.name?.split(" ")
                        var sign = ""
                        splitName?.forEach {
                            if (it.isNotEmpty()) {
                                sign += it[0] // index first value 0
                            }
                        }
                        ivContactIcon.visibility = View.GONE
                        mtvNameSign.visibility = View.VISIBLE
                        mtvNameSign.text = sign
                    }
                } else {
                    val splitName = contact.name?.split(" ")
                    var sign = ""
                    splitName?.forEach {
                        if (it.isNotEmpty())
                            sign += it[0] // index first value 0
                    }
                    ivContactIcon.visibility = View.GONE
                    mtvNameSign.visibility = View.VISIBLE
                    mtvNameSign.text = sign
                }
                mtvUserName.text = contact.name
                mtvUserMobile.text = contact.mobile
                mtvUserEmail.text = contact.email
            }
        }
    }
}
