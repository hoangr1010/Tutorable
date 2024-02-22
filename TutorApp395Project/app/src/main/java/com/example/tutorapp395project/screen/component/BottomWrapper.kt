package com.example.tutorapp395project.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
    Function: Creates the off white box that is used as the background for user input
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun LoginBox(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
) {
    Box (
        modifier = Modifier
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .wrapContentSize()

    ) {
        content()
    }
}
