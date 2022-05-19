package com.chat_server.controllers

import com.chat_server.data.data_source.ChatDataSource
import com.chat_server.data.models.ConnectedUser
import com.chat_server.data.models.Message
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatController(private val chatDataSource: ChatDataSource) {
    private val connectedUsers = ConcurrentHashMap<String, ConnectedUser>()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            while (true) {
                delay(3000L)
                println(connectedUsers.values)
            }
        }
    }

    fun onConnect(
        username: String,
        id: String,
        socket: WebSocketSession
    ) {
        if (connectedUsers.containsKey(username)) {
            throw Exception()
        }
        connectedUsers[username] = ConnectedUser(
            username = username,
            sessionId = id,
            socketSession = socket
        )
    }

    suspend fun sendMessage(senderUsername: String, message: String) {
        val messageEntity = Message(
            text = message,
            senderUserName = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        val parsedMessage = Json.encodeToString(messageEntity)
        connectedUsers.values.forEach { connectedUser ->
            connectedUser.socketSession.send(Frame.Text(parsedMessage))
        }
        chatDataSource.saveMessage(messageEntity)
    }

    suspend fun getAllMessages(): List<Message> {
        return chatDataSource.getAllMessages()
    }

    suspend fun getMessagesFromTimeStamp(timestamp: Long): List<Message> {
        return chatDataSource.getMessagesFromTimeStamp(timestamp)
    }

    suspend fun tryDisconnect(username: String) {
        connectedUsers[username]?.socketSession?.close()
        if (connectedUsers.containsKey(username)) {
            connectedUsers.remove(username)
        }
    }
}

