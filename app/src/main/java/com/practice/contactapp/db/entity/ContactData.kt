package com.practice.contactapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "contact")
@Parcelize
data class ContactData (
  @PrimaryKey (autoGenerate = true)
   var id : Int ?= null,
   var profile : ByteArray ?= null,
   var name : String ?= null,
   var mobile : String ?= null,
   var email : String ?= null,
   //var date: Date
) : Parcelable