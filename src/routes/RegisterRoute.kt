package com.chat_server.routes

import com.chat_server.controllers.UserController
import com.chat_server.data.models.User
import com.chat_server.exceptions.NoSuchUserException
import com.chat_server.exceptions.UserAlreadyExistsException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(controller: UserController) {
    route("/register") {
        post {
            val user = call.receive<User>()
            try{
                controller.tryRegister(user)
                call.respond(HttpStatusCode.OK)
            }catch(e: UserAlreadyExistsException){
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }
}