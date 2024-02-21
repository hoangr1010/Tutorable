package com.example.tutorapp395project.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val viewState = mutableStateOf("profile")
    val fields = listOf("schedule", "profile", "setting")

    fun changeViewState(newState: String) {
        viewState.value = newState
    }

}