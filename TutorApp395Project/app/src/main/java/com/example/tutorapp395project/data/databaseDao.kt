package com.example.tutorapp395project.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface databaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: userTable)


    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): userTable?
}