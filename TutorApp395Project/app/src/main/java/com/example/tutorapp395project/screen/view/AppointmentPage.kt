package com.example.tutorapp395project.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.R
import com.example.tutorapp395project.screen.studentView.TutorCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentPage(
    modifier: Modifier = Modifier,
){
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
                        text = "Appointment Confirmation",
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
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "",
                    )
                }
            }
        },

        ) { innerPadding ->
        LazyVerticalGrid(


            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFF00539C)),
            contentPadding = PaddingValues(16.dp),


            ){
            items(8){

                val painter = painterResource(id = R.drawable.image2)
                val name = "Roronoa Zoro"
                val subject = "Geography"
                TutorCard(
                    modifier,
                    painter,
                    name,
                    subject
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "icon")
        },
        title = {
            Text(text = dialogTitle,
                textAlign = TextAlign.Center,
                fontSize = 23.sp
                )
        },
        text = {
            Text(text = dialogText,
                textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm",
                    color = Color(0xFFEEA47F))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss",
                    textAlign = TextAlign.Left,
                    color = Color(0xFF003666))
            }
        }
    )
}


@Composable
fun ConfirmDialog() {
    // ...
    val openAlertDialog = remember { mutableStateOf(true) }

    // ...
    when {
        // ...
        openAlertDialog.value -> {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation later
                },
                dialogTitle = "Appointment Confirmation",
                dialogText = "Do you want to confirm this appointment?",
                icon = Icons.Default.Info
            )
        }
    }
}


@Preview
@Composable
fun previewAppointmentPage(){
//    BackgroundNoLogo()
//    AppointmentPage()
    ConfirmDialog()
}