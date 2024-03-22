package com.example.tutorapp395project.screen.tutorView


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.screen.studentView.BackgroundNoLogo
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.HomeViewModel
import com.example.tutorapp395project.viewModel.TutorViewModel


/**
 * Function : Display session information page (Tutor's perspective)
 * Param    :tutorViewModel: StudentViewModel,
 *           modifier: Modifier = Modifier,
 *           authViewModel: AuthViewModel,
 *           homeViewModel: HomeViewModel
 * Return   : None
 */
@Composable
fun SessionInfoDisplayT(modifier: Modifier = Modifier,
                       tutorViewModel: TutorViewModel,
                       authViewModel: AuthViewModel,
                       homeViewModel: HomeViewModel
){
    LazyColumn( contentPadding = PaddingValues(15.dp)
    ){
        item {
            FixedSessionInfoCard()
            FreeSessionInfoCard()
        }
    }
}


/**
 * Function : Creating 2 buttons (Edit and Cancel)
 * Param    : Void
 * Return   : None
 */
@Composable
fun SessionButtons(){
    Column(
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ElevatedButton(
                onClick = {/* TODO */ },
                modifier = Modifier
                    .size(width = 150.dp, height = 40.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66B8FF)),


                ) {
                Text("Edit", fontSize = 20.sp)
            }
            ElevatedButton(
                onClick = {/* TODO */ },
                modifier = Modifier
                    .size(width = 150.dp, height = 40.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB4A9)),


                ) {
                Text("Cancel", fontSize = 20.sp)
            }
        }
    }
}


/**
 * Function : Creating session information card (uneditable Information)
 * Param    : None
 * Return   : None
 */
@Composable
fun FixedSessionInfoCard(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 450.dp, height = 450.dp)
            .padding(3.dp)
    ) {
        Title("Session Information")
        FixedSessionInfo(123456, "Hawking", 6,"Maths")
    }
}


/**
 * Function : Creating session information card (editable Information)
 * Param    : None
 * Return   : None
 */
@Composable
fun FreeSessionInfoCard(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 450.dp, height = 330.dp)
            .padding(3.dp)
    ) {
        FreeSessionInfo("2024/04/04", "14:00-15:00")
        SessionButtons()
    }
}


/**
 * Function : Formatting title
 * Param    : title (String)
 * Return   : None
 */
@Composable
fun Title(title: String){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp),
        )
    }
    Divider(color = Color.Gray, thickness = 1.dp)
}


/**
 * Function : Formatting sub-title
 * Param    : title:String
 * Return   : None
 */
@Composable
fun Subtitle(subtitle: String){
    Text(
        subtitle,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(12.dp),
    )

}


/**
 * Function : Creating uneditable content for session information
 * Param    : sessionId: Int,
 *            studentName: String,
 *            subject: String
 * Return   : None
 */
@Composable
fun FixedSessionInfo(
    sessionId: Int,
    studentName: String,
    gradeLevel: Int,
    subject: String
) {
    LazyColumn(
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 12.dp),

            ) {
                Subtitle("Session ID")
                Text(
                    "$sessionId",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
                Subtitle("Student")
                Text(
                    studentName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
                Subtitle("Grade Level")
                Text(
                    "$gradeLevel",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
                Subtitle("Subject")
                Text(
                    subject,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
            }
        }

    }
}


/**
 * Function : Creating editable content for session information
 * Param    : dateIn: String,
 *            timeslot: String)
 * Return   : None
 */
@Composable
fun FreeSessionInfo(
    dateIn: String,
    timeslot: String) {
    LazyColumn(

    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 25.dp),
                //horizontalArrangement = Arrangement.Start,
                //verticalAlignment = Alignment.CenterVertically
            ) {

                Subtitle("Date")
                Text(
                    dateIn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
                Subtitle("Time")
                Text(
                    timeslot,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(12.dp),
                )
            }
        }

    }
}


@Preview
@Composable
fun PreviewSessionInfoPageT(){
    BackgroundNoLogo()
    SessionInfoDisplayT(tutorViewModel = TutorViewModel(
        authViewModel = AuthViewModel()
    ),
        authViewModel = AuthViewModel(),
        homeViewModel = HomeViewModel())

}