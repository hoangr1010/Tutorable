package com.example.tutorapp395project.viewModel

import android.graphics.PointF.length
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import kotlin.math.abs

class StudentViewModel: ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate =  mutableStateOf<LocalDate>(LocalDate.now())
    val selectedTimeSlotIdsSet = mutableStateOf<Set<Int>>(setOf())

    fun toggleTimeSlotId(id: Int) {
        val set = selectedTimeSlotIdsSet.value.toMutableSet()

        if (id in set) {
            set.remove(id)
        } else {

            // In case the length of the set is greater than 1, not allow to add more than 1 time slot
            if (set.size > 1) {
                return
            } else if (set.size == 1 && abs(set.toList()[0]-id) > 1) (
                // In case new time block is not consecutive to the existing time block, not allow to add
                return
            ) else {
                set.add(id)
            }

        }
        selectedTimeSlotIdsSet.value = set
    }


}