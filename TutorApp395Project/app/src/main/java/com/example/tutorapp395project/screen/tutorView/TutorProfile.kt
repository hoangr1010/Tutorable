package com.example.tutorapp395project.screen.tutorView

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.data.toTutor
import com.example.tutorapp395project.screen.studentView.ProfileField
import com.example.tutorapp395project.screen.studentView.ProfileName
import com.example.tutorapp395project.screen.studentView.ProfilePic
import com.example.tutorapp395project.viewModel.AuthViewModel

/*
    Function: Creates a column for all the tutors data will be displayed into
    Parameters: image -> students profile image
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun TutorProfileColumn(
        image: Int,
        authViewModel: AuthViewModel,
        modifier: Modifier
) {
    val tutor = toTutor(authViewModel.UserState.value)
    Log.d("TutorProfile", "Tutor: $tutor")

    Box(
        modifier = modifier
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
            ProfileName("${tutor.first_name} ${tutor.last_name}")
        }
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 300.dp)
        ) {
            val fields = listOf("email", "expertise", "experience", "verified_status", "description", "degrees", "date_of_birth")
            fields.forEach {field ->
                item {
                    ProfileField(title = field.uppercase(), value = when (field) {
                        "email" -> tutor.email
                        "expertise" -> tutor.expertise ?: "None"
                        "experience" -> tutor.experience
                        "degrees" -> tutor.degrees ?: "None"
                        "verified_status" -> tutor.verified_status.toString()
                        "date_of_birth" -> tutor.date_of_birth?.toString() ?: "None"
                        "description" -> tutor.description ?: "None"
                        else -> "None"
                    })
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewTutorProfile(){
//    TutorProfileColumn(12,AuthViewModel())
//}