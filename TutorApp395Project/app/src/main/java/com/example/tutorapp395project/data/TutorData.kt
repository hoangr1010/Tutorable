package com.example.tutorapp395project.data

import androidx.annotation.DrawableRes
import com.example.tutorapp395project.R

data class Tutor(
    val id: Int,
    val email: String,
    val role: String,
    val first_name: String,
    val last_name: String,
    val date_of_birth: String,
    val expertise: List<String>,
    val verified_status: Boolean,
    val experience: String,
    val description: String,
    val degrees: List<String>,
    val grade: Int,
    val school: String
)

data class TutorData(
    val name:String,
    val subject:String,
    @DrawableRes val tutorProfileImage:Int
)

data class TimeSlot (
    val id: Int,
    val time: String
)

data class AvailabilityState(
    var isLoading: Boolean = true,
    var time_block_id_list: List<Int> ?= emptyList()
)

data class GetAvailabilityRequest(
    val id: Int,
    val date: String
)

data class GetAvailabilityResponse(
    val time_block_id_list: List<Int>
)

data class AddAvailabilityRequest(
    val id: Int,
    val date: String,
    val time_block_id_list: List<Int>? = emptyList()
)

data class AddAvailabilityResponse(
    val date: String,
    val time_block_id_list: List<Int>
)




object TimeSlots {
    val slots = listOf(
        TimeSlot(1, "00:00-01:00"),
        TimeSlot(2, "01:00-02:00"),
        TimeSlot(3, "02:00-03:00"),
        TimeSlot(4, "03:00-04:00"),
        TimeSlot(5, "04:00-05:00"),
        TimeSlot(6, "05:00-06:00"),
        TimeSlot(7, "06:00-07:00"),
        TimeSlot(8, "07:00-08:00"),
        TimeSlot(9, "08:00-09:00"),
        TimeSlot(10, "09:00-10:00"),
        TimeSlot(11, "10:00-11:00"),
        TimeSlot(12, "11:00-12:00"),
        TimeSlot(13, "12:00-13:00"),
        TimeSlot(14, "13:00-14:00"),
        TimeSlot(15, "14:00-15:00"),
        TimeSlot(16, "15:00-16:00"),
        TimeSlot(17, "16:00-17:00"),
        TimeSlot(18, "17:00-18:00"),
        TimeSlot(19, "18:00-19:00"),
        TimeSlot(20, "19:00-20:00"),
        TimeSlot(21, "20:00-21:00"),
        TimeSlot(22, "21:00-22:00"),
        TimeSlot(23, "22:00-23:00"),
        TimeSlot(24, "23:00-00:00")
    )
}

