package com.chat_server.routes

import com.chat_server.controllers.UserController
import com.chat_server.data.models.User
import com.chat_server.exceptions.NoSuchUserException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(controller: UserController) {
    route("/login") {
        post {
            val user = call.receive<User>()
            try {
                controller.tryLogin(user)
                call.respond(HttpStatusCode.OK)
            } catch (e: NoSuchUserException) {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}