package com.chat_server

import com.chat_server.di.controllerModule
import com.chat_server.di.dataModule
import com.chat_server.di.dataSourceModule
import com.chat_server.di.tableModule
import com.chat_server.plugins.*
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(Koin) {
        modules(dataModule, dataSourceModule, tableModule, controllerModule)
    }
    configureSockets()
    configureRouting()
    configureSerialization()
    configureSecurity()
    configureStatusPages()
}

