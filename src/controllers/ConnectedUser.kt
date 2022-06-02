package com.chat_server.controllers

import io.ktor.http.cio.websocket.*

data class ConnectedUser(val username: String, val sessionId: String, val dialogId: Int, val socketSession: WebSocketSession)