package com.example.tutorapp395project.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tutorapp395project.data.AvailabilityRequest
import java.time.LocalDate

class TutorViewModel: ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate =  mutableStateOf<LocalDate>(LocalDate.now())
    val selectedTimeSlotIdsSet = mutableStateOf<Set<Int>>(setOf())

    fun toggleTimeSlotId(id: Int) {
        val set = selectedTimeSlotIdsSet.value.toMutableSet()
        if (id in set) {
            set.remove(id)
        } else {
            set.add(id)
        }
        selectedTimeSlotIdsSet.value = set
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetAvailability() {
        selectedDate.value = LocalDate.now()
        selectedTimeSlotIdsSet.value = setOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAvailability(id: String) {
        // save to database
        val availabilityRequest = AvailabilityRequest(
            id = id,
            date = selectedDate.value.toString(),
            time_block_id_list = selectedTimeSlotIdsSet.value.toList()
        )
        Log.d("TutorViewModel", "AvailabilityRequest: $availabilityRequest")
    }
}