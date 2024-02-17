package com.example.tutorapp395project.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tutorapp395project.classes.Navigation
<<<<<<< HEAD
import com.example.tutorapp395project.network.AuthService
=======
>>>>>>> fddd448e1c9c85c4e4966bce1170441f9d0360ce


class MainActivity : ComponentActivity() {
    lateinit var authService: AuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}
