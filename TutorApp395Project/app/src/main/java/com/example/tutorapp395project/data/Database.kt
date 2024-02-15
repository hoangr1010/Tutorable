package com.example.tutorapp395project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [userTable::class, tutor_session::class, tutor_availability::class], version = 1)
@TypeConverters(Converters::class) // Add this line to include TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): databaseDao
}
