package com.chat_server.routes

import com.chat_server.controllers.UserController
import constants.Routes
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.UserEntity

fun Route.loginRoute(userController: UserController) {
    route(Routes.Login.path) {
        post {
            val user = call.receive<UserEntity>()
            call.respond(HttpStatusCode.OK, userController.tryLogin(user))
        }
    }
}