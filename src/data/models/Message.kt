package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val senderUserName: String, val text: String, val timestamp: Long)