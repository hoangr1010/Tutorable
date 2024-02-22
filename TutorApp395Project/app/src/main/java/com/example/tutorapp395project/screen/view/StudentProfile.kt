package com.example.tutorapp395project.screen.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.data.Student
import com.example.tutorapp395project.data.toStudent
import com.example.tutorapp395project.viewModel.AuthViewModel

/*
    Function: Creates a column for all the students data will be displayed into
    Parameters: image -> students profile image
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun StudentProfileColumn(
    image: Int,
    authViewModel: AuthViewModel,
    modifier: Modifier
) {
    val student: Student = toStudent(authViewModel.UserState.value)
    Log.d("StudentProfile", "Student: $student")
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopCenter)
        ) {
            ProfilePic(image)
            ProfileName(name = "${student.first_name} ${student.last_name}")
        }
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 300.dp)
        ) {
            val fields = listOf("date_of_birth", "school", "grade", "email")
            fields.forEach {field ->
                item {
                    ProfileField(title = field, value = when (field) {
                        "date_of_birth" -> student.date_of_birth.toString()
                        "school" -> student.school
                        "grade" -> student.grade.toString()
                        "email" -> student.email
                        else -> ""
                    }.toString())
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

/*
    Function: Displays the profile pic in a circle
    Parameters: icon -> The image used for the home bar button
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun ProfilePic(image: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "image description",
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x80000000),
                ambientColor = Color(0x80000000)
            )
            .border(
                width = 5.dp,
                color = Color(0xFF0A74D2),
                shape = RoundedCornerShape(size = 170.dp)
            )
            .clip(CircleShape)
            .padding(5.dp)
            .width(170.dp)
            .height(170.dp),
        contentScale = ContentScale.Crop
    )

}

/*
    Function: Prints out the students name on the page
    Parameters: name -> Name of the student
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun ProfileName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        style = TextStyle(
            fontSize = 24.sp,
            //fontFamily = FontFamily(Font(R.font.roboto)),
            fontWeight = FontWeight(800),
            color = Color(0xFFEEA47F),
            textAlign = TextAlign.Center,
        )
    )
}

/*
    Function: Prints out the user's profile field and the description attached to the profile field
    Parameters: title -> The title of the field in the profile
                value -> The description of the field set by the user
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun ProfileField(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "$title",
            style = TextStyle(
                fontSize = 20.sp,
                //fontFamily = FontFamily(Font(roboto)),
                fontWeight = FontWeight(900),
                color = Color(0xFFEEA47F),
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "$value",
            style = TextStyle(
                fontSize = 15.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(300),
                color = Color(0xFFEEA47F)
            )
        )
    }
}
