package com.chat_server.di

import com.chat_server.data.tables.*
import org.koin.dsl.module

val tableModule = module {
    single<UsersTable> {
        UsersTable()
    }

    single<GeneralChatMessagesTable> {
        GeneralChatMessagesTable()
    }

    single<DialogsTable> {
        DialogsTable()
    }

    single<DialogMessagesTable> {
        DialogMessagesTable()
    }
}