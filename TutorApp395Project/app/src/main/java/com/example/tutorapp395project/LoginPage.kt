package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF55FFAD)
                ) {
                    Background()
                    LoginBox()
                    LoginButton()
                }
            }
        }
    }
}


@Composable
fun LoginBox(modifier: Modifier = Modifier) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(850.dp)
            .padding(top = 349.dp)
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    ){

    }
}

@Composable
fun LoginButton(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 15.dp))
            .width(235.dp)
            .height(44.dp)
            .background(color = Color(0xA309E27A), shape = RoundedCornerShape(size = 15.dp))
            .padding(start = 99.dp, top = 15.dp, end = 99.dp, bottom = 15.dp)

    ) {
        Text(
            text = "LOGIN",
            style = TextStyle(
                fontSize = 12.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
                ),
            modifier = Modifier
                .width(35.dp)
                .height(14.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun LoginPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        LoginBox()
        LoginButton()
    }
}