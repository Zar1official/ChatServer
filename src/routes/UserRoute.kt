package com.chat_server.routes

import com.chat_server.controllers.UserController
import constants.Routes
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userController: UserController) {
    route(Routes.Users.path) {
        get {
            call.respond(HttpStatusCode.OK, userController.getUsers())
        }
    }
}