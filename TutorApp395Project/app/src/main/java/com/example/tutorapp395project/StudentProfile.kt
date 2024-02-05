package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun StudentProfile(navController: NavController) {
    BackgroundNoLogo()
    HomeBar(navController = navController)
    StudentProfileColumn(R.drawable.student_id_photo)
}

/*
    Function: Creates a column for all the students data will be displayed into
    Parameters: image -> students profile image
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun StudentProfileColumn(image: Int, modifier: Modifier = Modifier) {
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
            ProfileName(name = "Tommy McStudent")
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 300.dp)
        ) {
            ProfileField(title = "Email", value = "tommyMcstudent@gmail.com")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileField(title = "Date of Birth", value = "September 4, 2010")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileField(title = "School", value = "Macewan Elementary")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileField(title = "Grade", value = "6")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileField(title = "Contact Number", value = "(780) 555-1234")
            Spacer(modifier = Modifier.height(10.dp))
            ProfileField(title = "Address", value = "1234 87 Ave NW Edmonton AB, T5T 8C5")
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

@Preview(showBackground = true)
@Composable
fun StudentProfilePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current))
        StudentProfileColumn(R.drawable.student_id_photo)
    }
}