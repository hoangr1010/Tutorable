package com.example.tutorapp395project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class StudentSchedule : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Background()
                    HomeBar()
                    Appointment()
                }
            }
        }
    }
}

@Composable
fun Appointment(modifier: Modifier = Modifier) {

}

@Composable
fun HomeBarOption(icon: Painter, option: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
        modifier = Modifier
            .padding(16.dp)
        ) {
        Text(
            text = "Register")
    }
}

@Composable
fun HomeBar(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            HomeBarOption(icon = painterResource(R.drawable.calendar), option = "Schedule",
                onClick = { Log.d("Schedule button", "Schedule Button clicked.") })
            HomeBarOption(icon = painterResource(R.drawable.profile), option = "Profile",
                onClick = { Log.d("Profile button", "Profile Button clicked.") })
            HomeBarOption(icon = painterResource(R.drawable.settings), option = "Settings",
                onClick = { Log.d("Settings button", "Settings Button clicked.") })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StudentSchedulePreview() {
    TutorApp395ProjectTheme {
        Background()
        HomeBar()
        Appointment()
    }
}