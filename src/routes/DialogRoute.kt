package com.chat_server.routes

import com.chat_server.controllers.ChatController
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.dialogRoute(chatController: ChatController) {

    route(Routes.DialogMessages.path) {
        get {
            val dialogId = call.parameters[Params.dialogIdParam]?.toInt() ?: 0
            call.respond(HttpStatusCode.OK, chatController.getDialogMessages(dialogId))
        }
    }

    route(Routes.GetDialog.path) {
        get {
            val userId = call.parameters[Params.userIdParam]?.toInt() ?: 0
            val companionId = call.parameters[Params.companionIdParam]?.toInt() ?: 0
            call.respond(HttpStatusCode.OK, chatController.getDialogBetweenUsers(userId, companionId))
        }
    }

    route(Routes.DialogList.path) {
        get {
            val userId = call.parameters[Params.userIdParam]?.toInt() ?: 0
            call.respond(HttpStatusCode.OK, chatController.getDialogs(userId))
        }
    }
}