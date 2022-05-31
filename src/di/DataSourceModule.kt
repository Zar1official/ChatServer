package com.chat_server.di

import com.chat_server.data.data_source.ChatDataSourceImpl
import com.chat_server.data.data_source.DialogDataSourceImpl
import com.chat_server.data.data_source.UserDataSourceImpl
import com.chat_server.data.data_source.contract.ChatDataSource
import com.chat_server.data.data_source.contract.DialogDataSource
import com.chat_server.data.data_source.contract.UserDataSource
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