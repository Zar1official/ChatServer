package com.chat_server.data.models

import io.ktor.http.cio.websocket.*

data class ConnectedUser(val username: String, val sessionId: String, val socketSession: WebSocketSession)