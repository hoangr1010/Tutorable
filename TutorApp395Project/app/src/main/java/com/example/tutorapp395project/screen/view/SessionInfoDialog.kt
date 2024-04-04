package com.example.tutorapp395project.screen.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
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
    opponentEmail: String,
    studentViewModel: StudentViewModel = StudentViewModel()
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
        ) {
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
                opponentEmail = opponentEmail,
                studentViewModel = studentViewModel
            )
        }
    }
}
    @RequiresApi(Build.VERSION_CODES.O)
    @Preview
    @Composable
    fun SessionInfoDialogPreview() {
        SessionInfoDialog(
            sessionId = 1,
            tutorName = "Tutor Name",
            subject = "Math",
            dateIn = "2024/04/01",
            timeslot = "Timeslot",
            opponentEmail = "email@gmail.com",
            onDelete = { },
            onDismiss = { },
            calendarState = UseCaseState()
        )
    }

