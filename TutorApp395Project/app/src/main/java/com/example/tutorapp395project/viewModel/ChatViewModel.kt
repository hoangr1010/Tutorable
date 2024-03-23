package com.example.tutorapp395project.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val client: ChatClient
): ViewModel() {

    fun createChannel(channelName: String, channelType: String = "messaging") {

        val trimmedChannelName = channelName.trim()
        val channelId = UUID.randomUUID().toString()

        viewModelScope.launch {

            client.createChannel(
                channelType = channelType,
                channelId = channelId,
                memberIds = emptyList(),
                extraData = mapOf(
                    "name" to trimmedChannelName,
                    "image" to "https://bit.ly/2TIt8NR"
                )
            ).enqueue { result ->

                if (result.isSuccess) {
                    Log.d("ChatViewModel", "Channel created successfully")
                } else {
                    Log.e("ChatViewModel", "Channel creation failed$result")
                }
            }

        }
    }
}