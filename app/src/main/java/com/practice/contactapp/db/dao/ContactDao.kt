package com.practice.contactapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practice.contactapp.db.entity.ContactData

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createContact(contact: ContactData)

    @Update
    suspend fun updateContact(contact: ContactData)

    @Delete
    suspend fun deleteContact(contact: ContactData)

    @Query("SELECT * FROM contact ORDER BY  id DESC")
    fun readContact(): LiveData<List<ContactData>>
}