package com.chat_server.controllers

import com.chat_server.controllers.contract.BaseController
import com.chat_server.data.models.*
import com.chat_server.data.repository.contract.Repository
import com.chat_server.exceptions.NoSuchDialogException
import com.chat_server.sessions.ChatSession
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatController(repository: Repository) : BaseController(repository) {
    private val connectedUsers = ConcurrentHashMap<String, ConnectedUser>()

    fun onConnectGeneralChat(
        username: String,
        id: String,
        socket: WebSocketSession,
        dialogId: Int
    ) {
        connectedUsers[username] =
            ConnectedUser(
                username = username,
                sessionId = id,
                socketSession = socket,
                dialogId = dialogId
            )
    }

    private suspend fun sendMessage(senderUsername: String, message: String) {
        val generalChatMessageEntity = GeneralChatMessage(
            text = message,
            senderUserName = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        val parsedMessage = Json.encodeToString(generalChatMessageEntity)
        val websocketEntity = SocketModel(type = SocketModelType.GeneralChatMessage.type, value = parsedMessage)
        val parsedEntity = Json.encodeToString(websocketEntity)

        repository.saveGeneralChatMessage(generalChatMessageEntity)
        connectedUsers.values.forEach { connectedUser ->
            connectedUser.socketSession.send(Frame.Text(parsedEntity))
        }
    }

    private suspend fun sendDialogMessage(dialogId: Int, sender: String, message: String) {
        val dialogMessageEntity = DialogMessage(
            dialogId = dialogId,
            sender = sender,
            text = message,
            timestamp = System.currentTimeMillis()
        )

        val parsedMessage = Json.encodeToString(dialogMessageEntity)
        val websocketEntity = SocketModel(type = SocketModelType.DialogMessage.type, value = parsedMessage)
        val parsedEntity = Json.encodeToString(websocketEntity)

        repository.saveDialogMessage(dialogMessageEntity)
        connectedUsers.values.forEach { connectedUser ->
            connectedUser.socketSession.send(Frame.Text(parsedEntity))
        }
    }

    suspend fun getAllMessages(): List<GeneralChatMessage> {
        return repository.getGeneralChatMessages()
    }

    suspend fun receiveSocketModel(session: ChatSession, model: SocketModel) {
        when (model.type) {
            SocketModelType.GeneralChatMessage.type -> {
                sendMessage(
                    senderUsername = session.username,
                    message = model.value
                )
            }
            SocketModelType.DialogMessage.type -> {
                sendDialogMessage(
                    dialogId = session.dialogId,
                    sender = session.username,
                    message = model.value
                )
            }
        }
    }

    suspend fun getDialogMessages(dialogId: Int): List<DialogMessage> {
        return repository.getDialogMessages(dialogId)
    }

    suspend fun getDialogs(userId: Int): List<Dialog> {
        return repository.getDialogs(userId)
    }

    suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): Dialog {
        val result = repository.getDialogBetweenUsers(userId, companionId)
        if (result == null) {
            repository.saveDialog(Dialog(creatorId = userId, companionId = companionId))
        }
        return repository.getDialogBetweenUsers(userId, companionId)?: throw NoSuchDialogException()
    }


    suspend fun tryDisconnect(username: String) {
        connectedUsers[username]?.socketSession?.close()
        if (connectedUsers.containsKey(username)) {
            connectedUsers.remove(username)
        }
    }
}



