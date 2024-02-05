package com.example.tutorapp395project.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
<<<<<<< HEAD:TutorApp395Project/app/src/main/java/com/example/tutorapp395project/screens/TutorProfile.kt
import com.example.tutorapp395project.R
=======
import androidx.navigation.NavController
>>>>>>> 33253c009102163d5de9ead1c37a24d35ae9c025:TutorApp395Project/app/src/main/java/com/example/tutorapp395project/TutorProfile.kt
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

@Composable
fun TutorProfile(navController: NavController) {
    BackgroundNoLogo()
    HomeBar(navController = navController)
    TutorProfileColumn(R.drawable.tutor_headshot)
}

/*
    Function: Creates a column for all the tutors data will be displayed into
    Parameters: image -> students profile image
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TutorProfileColumn(image: Int, modifier: Modifier = Modifier) {
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
            ProfileName("Karen McTutor")
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 300.dp)
        ) {
            ProfileField(title = "Email", value = "tommyMcstudent@gmail.com")
            ProfileField(title = "Date of Birth", value = "September 4, 2010")
            ProfileField(title = "School", value = "Macewan Elementary")
            ProfileField(title = "Grade", value = "6")
            ProfileField(title = "Contact Number", value = "(780) 555-1234")
            ProfileField(title = "Address", value = "1234 87 Ave NW Edmonton AB, T5T 8C5")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TutorProfilePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar(navController = NavController(LocalContext.current))
        TutorProfileColumn(R.drawable.tutor_headshot)
    }
}