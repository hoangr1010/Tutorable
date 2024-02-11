package com.example.tutorapp395project.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tutorapp395project.data.TutorLevel
import java.lang.invoke.TypeDescriptor

@Entity(tableName = "tutor")
data class tutorTable (
    @PrimaryKey(autoGenerate = true)
    val tutor_id: Int,
    val name: String,
    val email: String,
    val password: String,
    val expertise: Array<String>,
    val experience: TutorLevel,
    val verified_status: Boolean,
    val description: String,
    val degree: Array<String>,
    //val date_of_birth:
)

