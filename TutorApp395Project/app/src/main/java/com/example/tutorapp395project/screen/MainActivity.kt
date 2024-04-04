package com.example.tutorapp395project.screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorapp395project.classes.Navigation


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        // 1 - Set up the OfflinePlugin for offline storage
//        val offlinePluginFactory = StreamOfflinePluginFactory(appContext = applicationContext,)
//        val statePluginFactory = StreamStatePluginFactory(config = StatePluginConfig(), appContext = this)
//
//        // 2 - Set up the client for API calls and with the plugin for offline storage
//        val client = ChatClient.Builder("5evz2k4ym9pu", applicationContext)
//            .withPlugins(offlinePluginFactory, statePluginFactory)
//            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
//            .build()
//
//        // 3 - Authenticate and connect the user
//        val user = User(
//            id = "tutorial-droid",
//            name = "Tutorial Droid",
//            image = "https://bit.ly/2TIt8NR"
//        )
//
//        client.connectUser(
//            user = user,
//            token = client.devToken("tutorial-droid")
//        ).enqueue()


        setContent {
            Navigation()
            // Observe the client connection state
//            val clientInitialisationState by client.clientState.initializationState.collectAsState()
//
//
//
//            ChatTheme {
//
//                var showDialog by remember {
//                    mutableStateOf(false)
//                }
//
//                if (showDialog) {
//                    CreateChannelDialog(
//                        dismiss = { channelName ->
//                            chatViewModel.createChannel(channelName)
//                            showDialog = false
//                        }
//                    )
//                }
//
//
//                when (clientInitialisationState) {
//                    InitializationState.COMPLETE -> {
//                        ChannelsScreen(
//                            title = "hehe",
//                            isShowingSearch = true,
//                            onItemClick = { channel ->
//                                TODO()
//                            },
//                            onBackPressed = { finish() },
//                            onHeaderActionClick = {
//                                showDialog = true
//                            }
//                        )
//                    }
//                    InitializationState.INITIALIZING -> {
//                        Text(text = "Initializing...")
//                    }
//                    InitializationState.NOT_INITIALIZED -> {
//                        Text(text = "Not initialized...")
//                    }
//                }
//            }
//        }
    }
}

@Composable
private fun CreateChannelDialog(dismiss: (String) -> Unit) {

    var channelName by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dismiss(channelName) },
        title = {
            Text(text = "Enter Channel Name")
        },
        text = {
            TextField(
                value = channelName,
                onValueChange = {channelName = it}
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { dismiss(channelName) }
                ) {
                    Text(text = "Create Channel")
                }
            }
        }
    )
}
}

