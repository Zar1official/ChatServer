package com.chat_server.di

import com.chat_server.data.datasource.ChatDataSourceImpl
import com.chat_server.data.datasource.DialogDataSourceImpl
import com.chat_server.data.datasource.UserDataSourceImpl
import com.chat_server.data.datasource.contract.ChatDataSource
import com.chat_server.data.datasource.contract.DialogDataSource
import com.chat_server.data.datasource.contract.UserDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<UserDataSource> {
        return@single UserDataSourceImpl(db = get(), usersTable = get())
    }

    single<ChatDataSource> {
        return@single ChatDataSourceImpl(db = get(), generalChatMessagesTable = get())
    }

    single<DialogDataSource> {
        return@single DialogDataSourceImpl(db = get(), dialogMessagesTable = get(), dialogsTable = get())
    }
}