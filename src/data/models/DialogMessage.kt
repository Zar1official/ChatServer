package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DialogMessage(
    val messageId: Int = 0,
    val dialogId: Int,
    val sender: String,
    val timestamp: Long,
    val text: String
)