package com.chat_server.routes

import com.chat_server.controllers.ChatController
import com.chat_server.sessions.ChatSession
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatRoute(chatController: ChatController) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }
        try {
            chatController.onConnect(
                username = session.username,
                id = session.id,
                socket = this
            )
            incoming.consumeEach { frame ->
                println(frame.data)
                if (frame is Frame.Text) {
                    chatController.sendMessage(
                        senderUsername = session.username,
                        message = frame.readText()
                    )
                }
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            chatController.tryDisconnect(session.username)
        }
    }

    route("/messages") {
        get {
            val timestamp = call.parameters["timestamp"]?.toLong()
            if (timestamp == null) {
                call.respond(
                    HttpStatusCode.OK,
                    chatController.getAllMessages()
                )
            } else {
                call.respond(
                    chatController.getMessagesFromTimeStamp(timestamp)
                )
            }
        }
    }
}