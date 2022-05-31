package com.chat_server.routes

import com.chat_server.controllers.ChatController
import com.chat_server.data.models.SocketModel
import com.chat_server.data.models.SocketModelType
import com.chat_server.sessions.ChatSession
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.generalChatRoute(chatController: ChatController) {
    webSocket(Routes.GeneralChat.path) {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }
        chatController.onConnectGeneralChat(
            username = session.username,
            id = session.id,
            socket = this,
            dialogId = session.dialogId
        )
        incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                val entity = Json.decodeFromString<SocketModel>(frame.readText())
                chatController.receiveSocketModel(session, entity)
            }
        }
        chatController.tryDisconnect(session.username)
    }

    route(Routes.GeneralChatMessages.path) {
        get {
                call.respond(
                    HttpStatusCode.OK,
                    chatController.getAllMessages()
                )
        }
    }
}