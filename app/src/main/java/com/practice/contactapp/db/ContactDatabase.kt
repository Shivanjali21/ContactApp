package com.practice.contactapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.contactapp.db.dao.ContactDao
import com.practice.contactapp.db.entity.ContactData

@Database(entities = [ContactData::class], version = 6, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private const val DATABASE_NAME = "contact_db"
        @Volatile
        var instance : ContactDatabase? = null
        private val LOCK = Any()

         operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:
            createDatabase(context).also { instance = it }
         }

         private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactDatabase::class.java,
            DATABASE_NAME
         ).build()
    }
}