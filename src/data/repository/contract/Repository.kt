package com.chat_server.data.repository.contract

import com.chat_server.data.models.*

interface Repository {
    suspend fun register(user: User): Boolean
    suspend fun login(user: User): Boolean
    suspend fun saveUser(user: User)
    suspend fun getUserByUserName(username: String): User?
    suspend fun getUsers(): List<User>

    suspend fun getGeneralChatMessages(): List<GeneralChatMessage>
    suspend fun saveGeneralChatMessage(message: GeneralChatMessage)

    suspend fun saveDialog(dialog: Dialog)
    suspend fun saveDialogMessage(message: DialogMessage)
    suspend fun getDialogMessages(dialogId: Int): List<DialogMessage>
    suspend fun getDialogs(userId: Int): List<Dialog>
    suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): Dialog?
}