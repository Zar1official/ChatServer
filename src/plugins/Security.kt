package com.chat_server.plugins

import com.chat_server.sessions.ChatSession
import constants.Params
import io.ktor.application.*
import io.ktor.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters[Params.USERNAME_PARAM] ?: "Guest"
            val dialogId = call.parameters[Params.DIALOG_ID_PARAM]?.toInt() ?: 0
            call.sessions.set(ChatSession(username, generateNonce(), dialogId))
        }
    }
}