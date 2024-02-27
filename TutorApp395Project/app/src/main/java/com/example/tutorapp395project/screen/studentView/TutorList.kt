package com.example.tutorapp395project.screen.studentView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp395project.R
import com.example.tutorapp395project.data.TutorRepo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorList(
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
                        text = "Say hi to your tutor",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
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
            items(10){

                val painter = painterResource(id = R.drawable.image2)
                val name = "Roronoa Zoro"
                val subject = "Geography"
                TutorCard(
                    modifier,
                    painter,
                    name,
                    subject
                )

                val painter1 = painterResource(id = R.drawable.image2)
                val name1 = "Roronoa Zoro "
                val subject1 = "Geography"
                TutorCard(
                    modifier,
                    painter1,
                    name1,
                    subject1
                )
            }
        }
    }
}



@Composable
fun TutorCard( modifier:Modifier,
              painter: Painter,
              name: String,
              subject: String){

    Card(
        modifier
            .size(200.dp)
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter

        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painter,
                    contentDescription = name,
                    modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier.padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                    )
                    Text(
                        text = subject,
                        fontSize = 18.sp,
                    )


                }
            }
        }
    }
}




@Preview
@Composable
fun previewTutorList(){
    BackgroundNoLogo()
    TutorList()
}