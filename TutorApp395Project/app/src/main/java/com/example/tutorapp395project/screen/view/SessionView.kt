package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SessionView(time: String,
                date: String,
                subject: String,
                with: String,
                person: String,
                onClick: () -> Unit = {},
                modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .clickable { onClick() }
        ) {
            Column (
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
                modifier = modifier.padding(5.dp)
            ) {
                Text(
                    text = "time: $time\ndate: $date\nsubject: $subject\n\n",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                )
                Text(
                    text = "$with: $person\n",
                    style = TextStyle(
                        fontSize = 12.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun SessionViewPreview() {
    SessionView(
        time = "3:00-4:00",
        date = "10/10/2021",
        subject = "Math",
        with = "With",
        person = "John Doe"
    )
}