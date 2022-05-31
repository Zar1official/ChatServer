package com.chat_server.routes

import com.chat_server.controllers.UserController
import com.chat_server.data.models.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(userController: UserController) {
    route(Routes.Register.path) {
        post {
            val user = call.receive<User>()
            call.respond(HttpStatusCode.OK, userController.tryRegister(user))
        }
    }
}