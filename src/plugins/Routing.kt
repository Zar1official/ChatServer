package com.chat_server.plugins

import com.chat_server.controllers.ChatController
import com.chat_server.controllers.UserController
import com.chat_server.routes.*
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userController by inject<UserController>()
    val chatController by inject<ChatController>()

    install(Routing) {
        loginRoute(userController)
        registerRoute(userController)
        generalChatRoute(chatController)
        dialogRoute(chatController)
        userRoute(userController)
    }
}