package com.example.tutorapp395project.screen.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.tutorapp395project.viewModel.AuthViewModel
import com.example.tutorapp395project.viewModel.StudentViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionInfoDialog(
    sessionId: Int,
    tutorName: String,
    subject: String,
    dateIn: String,
    timeslot: String,
    onDismiss: () -> Unit = { },
    onDelete: () -> Unit = { },
    calendarState: UseCaseState = UseCaseState(),
    studentViewModel: StudentViewModel = StudentViewModel()
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
        ){
            FixedSessionInfoCard(
                sessionId = sessionId,
                tutorName = tutorName,
                subject = subject,
            )
            FreeSessionInfoCard(
                dateIn = dateIn,
                timeslot = timeslot,
                onDelete = onDelete,
                calendarState = calendarState,
                studentViewModel = studentViewModel
            )
        }
    }
}
