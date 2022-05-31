package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Dialog(val dialogId: Int = 0, val creatorId: Int, val companionId: Int)