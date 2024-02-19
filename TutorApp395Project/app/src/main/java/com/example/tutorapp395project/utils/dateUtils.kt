package com.example.tutorapp395project.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun stringToDate(dateString: String) : Date? {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return isoFormat.parse(dateString)
}