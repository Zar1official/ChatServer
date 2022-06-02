package com.chat_server.di

import com.chat_server.data.db.DataBase
import com.chat_server.data.repository.RepositoryImpl
import com.chat_server.data.repository.contract.Repository
import org.koin.dsl.module

val dataModule = module {

    single<Repository> { RepositoryImpl(chatDataSource = get(), userDataSource = get(), dialogDataSource = get()) }

    single<DataBase> {
        DataBase()
    }
}