package com.example.tutorapp395project.utils

import com.example.tutorapp395project.data.TimeSlots
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun stringToDate(dateString: String) : Date {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return isoFormat.parse(dateString) ?: throw IllegalArgumentException("Invalid date format")
}

fun stringToReadableDate(dateString: String) : String {
    val date = stringToDate(dateString)
    val readableFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return readableFormat.format(date)
}

fun getTimeInterval(timeSlotIds: List<Int>): String {
    return when {
        timeSlotIds.size == 1 -> {
            TimeSlots.slots.find { it.id == timeSlotIds[0] }?.time ?: "Invalid time ID"
        }
        timeSlotIds.size == 2 && timeSlotIds[1] - timeSlotIds[0] == 1 -> {
            val start = TimeSlots.slots.find { it.id == timeSlotIds[0] }?.time?.split("-")?.get(0) ?: "Invalid ID"
            val end = TimeSlots.slots.find { it.id == timeSlotIds[1] }?.time?.split("-")?.get(1) ?: "Invalid ID"
            "$start-$end"
        }
        else -> {
            "Invalid IDs"
        }
    }
}