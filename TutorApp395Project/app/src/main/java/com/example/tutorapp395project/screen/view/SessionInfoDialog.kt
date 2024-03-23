package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun SessionInfoDialog(
    sessionId: Int,
    tutorName: String,
    subject: String,
    dateIn: String,
    timeslot: String,
    onDismiss: () -> Unit = { },
    onDelete: () -> Unit = { }
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
                onDelete = onDelete
            )
        }
    }
}
