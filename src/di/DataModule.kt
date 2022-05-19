package com.chat_server.di

import com.chat_server.controllers.ChatController
import com.chat_server.controllers.UserController
import com.chat_server.data.data_source.ChatDataSource
import com.chat_server.data.data_source.ChatDataSourceImpl
import com.chat_server.data.data_source.UserDataSource
import com.chat_server.data.data_source.UserDataSourceImpl
import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.MessagesTable
import com.chat_server.data.tables.UsersTable
import org.koin.dsl.module

val dataModule = module {

    single<UserDataSource> {
        return@single UserDataSourceImpl(db = get(), usersTable = get())
    }

    single<ChatDataSource> {
        ChatDataSourceImpl(db = get(), messagesTable = get())
    }

    single<DataBase> {
        DataBase(usersTable = get(), messagesTable = get())
    }

    single<UsersTable> {
        UsersTable()
    }

    single<MessagesTable> {
        MessagesTable()
    }

    single<UserController> {
        UserController(userDataSource = get())
    }

    single<ChatController> {
        ChatController(chatDataSource = get())
    }
}