package com.example.tutorapp395project.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
<<<<<<< HEAD:TutorApp395Project/app/src/main/java/com/example/tutorapp395project/screens/StudentSchedule.kt
import com.example.tutorapp395project.R
=======
import androidx.navigation.NavController
>>>>>>> 33253c009102163d5de9ead1c37a24d35ae9c025:TutorApp395Project/app/src/main/java/com/example/tutorapp395project/StudentSchedule.kt
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun StudentSchedule(navController: NavController) {
    BackgroundNoLogo()
    HomeBar(navController = navController)
    StudentAppointmentLayout()
}

/*
    Function: This creates a column that lays out all the users scheduled appointments
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun StudentAppointmentLayout(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    )
        {
        Appointment("3:00PM - 4:00PM", "January 24th, 2024", "Math",
                    "Tutor","Karen McTutor")
        Appointment("4:00PM - 5:00PM", "January 24th, 2024", "History",
                    "Tutor","Karen McTutor")
    }
}

/*
    Function: This shows the scheduled appointment on the users schedule page
    Parameters: time -> time in which the appointment is scheduled
                date -> date for the session
                subject -> subject for the tutor session
                with -> tutor or student
                person -> name of the person
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun Appointment(time: String, date: String, subject: String, with: String, person: String,
                modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(120.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(size = 15.dp)
                )
        ) {
            Text(
                text = "$time\n$date\n$subject\n\n",
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    )
            )
            Text(
                text = "$with: $person\n",
                modifier = Modifier
                    .padding(start = 10.dp, top = 90.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                    )
            )
        }
    }
}

/*
    Function: Creates just a simple blue background without a logo
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun BackgroundNoLogo(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ){}
}

/*
    Function: Creates a single homebar option that the user can use to navigate to the
              corresponding page
    Parameters: icon -> The image used for the home bar button
                option -> the name of the page the user wants to navigate to
                onClick -> Link to the next activity being accessed
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun HomeBarOption(icon: Painter, option: String, navController: NavController, target: String,
                  modifier: Modifier = Modifier) {
    Button(
        onClick = {navController.navigate(target)},
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
        modifier = Modifier
            .padding(16.dp)
        ) {
        Text(
            text = "$option",
            style = TextStyle(color = Color(0xFFB24444))
        )
    }
}

/*
    Function: Creates the homebar that the user will use to navigate between pages
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun HomeBar(navController: NavController, modifier: Modifier = Modifier) {
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
            Row(
            ){
                HomeBarOption(icon = painterResource(R.drawable.calendar), option = "Schedule",
                    navController = navController, target = Screen.StudentSchedule.route)
                HomeBarOption(icon = painterResource(R.drawable.profile), option = "Profile",
                    navController = navController, target = Screen.StudentProfile.route)
                HomeBarOption(icon = painterResource(R.drawable.settings), option = "Settings",
                    navController = navController, target = Screen.Settings.route)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StudentSchedulePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current))
        StudentAppointmentLayout()
    }
}