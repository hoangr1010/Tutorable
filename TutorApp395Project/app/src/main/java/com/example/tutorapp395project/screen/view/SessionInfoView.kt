package com.example.tutorapp395project.screen.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import kotlinx.coroutines.launch

/**
 * Function : Creating session information card (uneditable Information)
 * Param    : sessionId: Int
 *            tutorName: String
 *            subject: String
 * Return   : None
 */
@Composable
fun FixedSessionInfoCard(
    sessionId: Int,
    tutorName: String,
    subject: String,
){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .wrapContentHeight()
            .padding(3.dp)
    ) {
        Title("Session Information")
        FixedSessionInfo(sessionId, tutorName, subject)
    }
}


/**
 * Function : Creating session information card (editable Information)
 * Param    : dateIn: String
 *            timeslot: String
 *            onDelete: () -> Unit
 * Return   : None
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FreeSessionInfoCard(
    dateIn: String,
    timeslot: String,
    onDelete: () -> Unit = { },
    calendarState: UseCaseState,
    studentViewModel: StudentViewModel
){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .wrapContentHeight()
            .padding(3.dp)
    ) {
        FreeSessionInfo(dateIn, timeslot, onDelete, calendarState, studentViewModel)
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
            .padding(start = 16.dp, 5.dp),
    )

}


/**
 * Function : Creating uneditable content for session information
 * Param    : sessionId: Int
 *            tutorName: String
 *            subject: String
 * Return   : None
 */
@Composable
fun FixedSessionInfo(
    sessionId: Int,
    tutorName: String,
    subject: String,
) {
    LazyColumn(

    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp),
                //horizontalArrangement = Arrangement.Start,
                //verticalAlignment = Alignment.CenterVertically
            ) {
                Subtitle("Session ID")
                Text(
                    "$sessionId",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                )
                Subtitle("Tutor")
                Text(
                    tutorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                )
                Subtitle("Subject")
                Text(
                    subject,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                )
            }
        }

    }
}

/**
 * Function : Creating editable content for session information
 * Param    : dateIn: String,
 *            timeslot: String
 *            onDelete: () -> Unit
 * Return   : None
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FreeSessionInfo(
    dateIn: String,
    timeslot: String,
    onDelete: () -> Unit = { },
    calendarState: UseCaseState,
    studentViewModel: StudentViewModel,
) {

    LazyColumn{
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 25.dp),
                //horizontalArrangement = Arrangement.Start,
                //verticalAlignment = Alignment.CenterVertically
            ) {
                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current

                Subtitle("Date")
                Text(
                    dateIn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                )
                Subtitle("Time")
                Text(
                    timeslot,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
//                            val intent = Intent(Intent.ACTION_SENDTO).apply {
//                                data = Uri.parse("mailto:${}")
//                            }
//                            context.startActivity(intent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp,)
                    ,
                    shape = RoundedCornerShape(25),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66B8FF)),


                    ) {
                    Text("Send Email", fontSize = 20.sp)
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            calendarState.show()
                            studentViewModel.isEditing.value = true
                            Log.d("FreeSessionInfo", "Edit button clicked: ${studentViewModel.isEditing.value}")
                                  },
                        modifier = Modifier
                            .height(40.dp),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66B8FF)),


                        ) {
                        Text("Edit", fontSize = 20.sp)
                    }
                    Button(
                        onClick = { onDelete() },
                        modifier = Modifier
                            .height(40.dp)
                        ,
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB4A9)),


                        ) {
                        Text("Cancel", fontSize = 20.sp)
                    }
                }
            }
        }

    }
}
