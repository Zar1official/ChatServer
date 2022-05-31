package com.chat_server.di

import com.chat_server.controllers.ChatController
import com.chat_server.controllers.UserController
import org.koin.dsl.module

val controllerModule = module {
    single<UserController> {
        UserController(repository = get())
    }

    single<ChatController> {
        ChatController(repository = get())
    }
}