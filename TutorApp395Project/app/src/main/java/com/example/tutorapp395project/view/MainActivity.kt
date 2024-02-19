package com.example.tutorapp395project.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tutorapp395project.classes.Navigation
import com.example.tutorapp395project.data.remote.AuthService


class MainActivity : ComponentActivity() {
    lateinit var authService: AuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}
