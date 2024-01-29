package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class TutorSchedule : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackgroundNoLogo()
                    HomeBar()
                    TutorAppointmentLayout()
                }
            }
        }
    }
}
@Composable
fun TutorAppointmentLayout(modifier: Modifier = Modifier) {
    Column {
        Appointment("3:00PM - 4:00PM", "January 24th, 2024", "Math",
            "Tommy McStudent")
    }


}

@Preview(showBackground = true)
@Composable
fun TutorSchedulePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar()
        TutorAppointmentLayout()
    }
}