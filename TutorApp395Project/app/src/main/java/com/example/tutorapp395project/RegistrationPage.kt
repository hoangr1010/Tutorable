package com.example.tutorapp395project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class RegistrationPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Background()
                    LoginBox()
                    RegistrationText()
                }
            }
        }
    }
}

@Composable
fun RegistrationText(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(840.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Choose Your Role",
            style = TextStyle(
                fontSize = 35.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(900),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            ) ,
            modifier = Modifier
                .width(282.dp)
                .height(41.dp)
        )
        StudentButton(painterResource(R.drawable.student), "Student",
                      onClick = { Log.d("Student button", "Student Button clicked.")})
        TutorButton(painterResource(R.drawable.teacher), "Teacher",
            onClick = { Log.d("Student button", "Student Button clicked.")})
    }
}

@Composable
fun StudentButton(icon: Painter, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        content = {
            // Specify the icon using the icon parameter
            Image(painter = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing
            Text(text, fontSize = 40.sp)
        },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
    )
}

@Composable
fun TutorButton(icon: Painter, text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        content = {
            // Specify the icon using the icon parameter
            Image(painter = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing
            Text(text, fontSize = 40.sp)
        },
        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F))
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationPagePreview() {
    TutorApp395ProjectTheme {
        Background()
        LoginBox()
        RegistrationText()
    }
}