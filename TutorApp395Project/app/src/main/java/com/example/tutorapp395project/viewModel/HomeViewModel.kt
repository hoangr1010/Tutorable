package com.example.tutorapp395project.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class HomeViewModel: ViewModel() {

    val viewState = mutableStateOf("schedule")
    val fields = listOf("schedule", "profile", "setting")

    fun changeViewState(newState: String) {
        viewState.value = newState
    }

}