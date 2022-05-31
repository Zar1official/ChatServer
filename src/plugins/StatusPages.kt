package com.chat_server.plugins

import com.chat_server.exceptions.NoSuchDialogException
import com.chat_server.exceptions.NoSuchUserException
import com.chat_server.exceptions.UserAlreadyExistsException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { e ->
            when (e) {
                is NoSuchUserException -> call.respond(HttpStatusCode.NotFound, "No such user found")
                is UserAlreadyExistsException -> call.respond(HttpStatusCode.NotFound, "Such user already exists")
                is NoSuchDialogException -> call.respond(HttpStatusCode.NotFound, "No such dialog")
            }
        }
    }
}