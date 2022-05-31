package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GeneralChatMessage(val messageId: Int = 0, val senderUserName: String, val text: String, val timestamp: Long)