package com.chat_server

import com.chat_server.di.dataModule
import com.chat_server.plugins.configureRouting
import com.chat_server.plugins.configureSecurity
import com.chat_server.plugins.configureSerialization
import com.chat_server.plugins.configureSockets
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(Koin) {
        modules(dataModule)
    }
    configureSockets()
    configureRouting()
    configureSerialization()
    configureSecurity()
}

