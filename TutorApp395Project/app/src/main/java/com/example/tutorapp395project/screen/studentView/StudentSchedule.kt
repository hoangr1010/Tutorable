@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tutorapp395project.screen.studentView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentAppointmentLayout(modifier: Modifier = Modifier) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = Color(0xFF00539C))

        ) {
            items(10) {
                Appointment(
                    "3:00PM - 4:00PM", "January 24th, 2024", "Math",
                    "Tutor", "Karen McTutor"
                )

            }
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
                .padding(2.dp)
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


@Preview
@Composable
fun PreviewStudentSChedule(){
    BackgroundNoLogo()
    StudentAppointmentLayout()
//    Appointment("3:00PM", "024/02/21","math","Karen McTutor", "Nami" )

}