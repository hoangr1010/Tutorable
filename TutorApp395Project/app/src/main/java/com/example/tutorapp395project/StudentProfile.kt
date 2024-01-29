package com.example.tutorapp395project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.R.font.roboto
import com.example.tutorapp395project.ui.theme.TutorApp395ProjectTheme

class StudentProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorApp395ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackgroundNoLogo()
                    HomeBar()
                    StudentProfileColumn(R.drawable.student_id_photo)
                }
            }
        }
    }
}

@Composable
fun StudentProfileColumn(image: Int, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        ProfilePic(image)
        ProfileName(name = "Tommy McStudent")
        ProfileField(title = "Email", value = "tommyMcstudent@gmail.com")
        ProfileField(title = "Date of Birth", value = "September 4, 2010")
        ProfileField(title = "School", value = "Macewan Elemntary")
        ProfileField(title = "Grade", value = "6")
        ProfileField(title = "Contact Number", value = "(780) 555-1234")
        ProfileField(title = "Address", value = "1234 87 Ave NW Edmonton AB, T5T 8C5")
    }
}

@Composable
fun ProfilePic(image: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "image description",
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x80000000),
                ambientColor = Color(0x80000000)
            )
            .border(
                width = 5.dp,
                color = Color(0xFF0A74D2),
                shape = RoundedCornerShape(size = 170.dp)
            )
            .clip(CircleShape)
            .padding(5.dp)
            .width(170.dp)
            .height(170.dp),
        contentScale = ContentScale.Crop
    )

}

@Composable
fun ProfileName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        style = TextStyle(
            fontSize = 24.sp,
            //fontFamily = FontFamily(Font(R.font.roboto)),
            fontWeight = FontWeight(800),
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
        )
    )
}

@Composable
fun ProfileField(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "$title",
            style = TextStyle(
                fontSize = 15.sp,
                //fontFamily = FontFamily(Font(roboto)),
                fontWeight = FontWeight(900),
                color = Color(0xFF000000),
            )
        )
        Text(
            text = "$value",
            style = TextStyle(
                fontSize = 12.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(300),
                color = Color(0xFF000000)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfilePreview() {
    TutorApp395ProjectTheme {
        BackgroundNoLogo()
        HomeBar()
        StudentProfileColumn(R.drawable.student_id_photo)
    }
}