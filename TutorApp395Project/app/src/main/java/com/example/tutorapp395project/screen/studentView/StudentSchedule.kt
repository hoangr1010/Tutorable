@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tutorapp395project.screen.studentView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
    Function: This creates a column that lays out all the users scheduled appointments
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
/*@Composable
fun StudentAppointmentLayout(modifier: Modifier = Modifier) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    )
        {
            items(19) {
                Appointment("3:00PM - 4:00PM", "January 24th, 2024", "Math",
                    "Tutor","Karen McTutor")

            }
        }
}*/


@Composable
fun StudentAppointmentLayout(modifier: Modifier = Modifier) {
    var presses by remember { mutableIntStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = (Color(0xFFEEA47F)),
                    titleContentColor = Color(0xFF191C1D),
                ),
                title = {
                    Text(modifier = Modifier
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Schedule",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Backspace"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = (Color(0xFFEEA47F)),
                contentColor = Color(0xFF191C1D),
            ) {
//                Button(onClick = {/* TODO */ },
//                    colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
//                    modifier = Modifier
//                        .fillMaxWidth(0.7f)
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        text = "Bottom app bar",
//                    )
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                    )
                }
            }
//            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ },
                containerColor = Color(0xFF66B8FF),
                contentColor = Color(0xFFCCE7FF)) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xFF00539C))

        ) {
            items(19) {
                Appointment(
                    "3:00PM - 4:00PM", "January 24th, 2024", "Math",
                    "Tutor", "Karen McTutor"
                )

            }
        }
    }

}

/*
    Function: This shows the scheduled appointment on the users schedule page
    Parameters: time -> time in which the appointment is scheduled
                date -> date for the session
                subject -> subject for the tutor session
                with -> tutor or student
                person -> name of the person
                modifier -> takes modifier parameters
    Return: None
 */
@Composable
fun Appointment(time: String, date: String, subject: String, with: String, person: String,
                modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(120.dp)
                .padding(2.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(size = 15.dp)

                )
        ) {
            Text(
                text = "$time\n$date\n$subject\n\n",
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    )
            )
            Text(
                text = "$with: $person\n",
                modifier = Modifier
                    .padding(start = 10.dp, top = 90.dp),
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

/*
    Function: Creates just a simple blue background without a logo
    Parameters: modifier -> takes modifier parameters
    Return: None
 */

@Composable
fun BackgroundNoLogo(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF00539C))
    ){}
}

/*
    Function: Creates a single homebar option that the user can use to navigate to the
              corresponding page
    Parameters: icon -> The image used for the home bar button
                option -> the name of the page the user wants to navigate to
                onClick -> Link to the next activity being accessed
                modifier -> takes modifier parameters
    Return: None
 */
//@Composable
//fun HomeBarOption(icon: Painter, option: String, navController: NavController, target: String,
//                  modifier: Modifier = Modifier) {
//    Button(
//        onClick = {navController.navigate(target)},
//        colors = ButtonDefaults.buttonColors(Color(0xFFEEA47F)),
//        modifier = Modifier
//            .padding(16.dp)
//        ) {
//        Text(
//            text = "$option",
//            style = TextStyle(color = Color(0xFFB24444))
//        )
//    }
//}

/*
    Function: Creates the homebar that the user will use to navigate between pages
    Parameters: modifier -> takes modifier parameters
    Return: None
 */
//@Composable
//fun HomeBar(navController: NavController, modifier: Modifier = Modifier, route: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Bottom,
//        modifier = Modifier
//            .fillMaxSize()
//    ){
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFFD9D9D9),
//                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
//                )
//        ) {
//            Row {
//                if (route == "student") {
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.calendar), option = "Schedule",
//                        navController = navController, target = Screen.StudentSchedule.route
//                    )
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.profile), option = "Profile",
//                        navController = navController, target = Screen.StudentProfile.route
//                    )
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.settings), option = "Settings",
//                        navController = navController, target = Screen.Settings.route + "/$route"
//                    )
//                }
//                if (route == "tutor") {
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.calendar), option = "Schedule",
//                        navController = navController, target = Screen.TutorSchedule.route
//                    )
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.profile), option = "Profile",
//                        navController = navController, target = Screen.TutorProfile.route
//                    )
//                    HomeBarOption(
//                        icon = painterResource(R.drawable.settings), option = "Settings",
//                        navController = navController, target = Screen.Settings.route + "/$route"
//                    )
//
//                }
//            }
//        }
//    }
//}
//

@Preview
@Composable
fun PreviewStudentSChedule(){
    BackgroundNoLogo()
    StudentAppointmentLayout()
//    Appointment("3:00PM", "024/02/21","math","Karen McTutor", "Nami" )

}