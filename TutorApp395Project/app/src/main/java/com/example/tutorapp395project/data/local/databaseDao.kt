package com.example.tutorapp395project.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (Dao) : for query's on database
@Dao
interface databaseDao {

    // Insertion: using suspend for coroutines, add a user into room database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: userTable)

    // Delete: Deletes user on the parameter from the database
    @Delete
    fun deleteUser(user: userTable)

    // ID Search: Query database for user ID
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): userTable?


}