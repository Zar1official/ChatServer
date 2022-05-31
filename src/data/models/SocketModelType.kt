package com.chat_server.data.models

sealed class SocketModelType(val type: String) {
    object GeneralChatMessage : SocketModelType("message")
    object DialogMessage: SocketModelType("dialog-message")
}