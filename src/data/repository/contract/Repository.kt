package com.chat_server.data.repository.contract

import com.chat_server.data.models.*
import models.DialogEntity
import models.DialogMessageEntity
import models.GeneralChatMessageEntity
import models.UserEntity

interface Repository {
    suspend fun register(userEntity: UserEntity): Boolean
    suspend fun login(userEntity: UserEntity): Boolean
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun getUserByUserName(username: String): UserEntity?
    suspend fun getUsers(): List<UserEntity>

    suspend fun getGeneralChatMessages(): List<GeneralChatMessageEntity>
    suspend fun saveGeneralChatMessage(messageEntity: GeneralChatMessageEntity)

    suspend fun saveDialog(dialogEntity: DialogEntity)
    suspend fun saveDialogMessage(messageEntity: DialogMessageEntity)
    suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity>
    suspend fun getDialogs(userId: Int): List<DialogEntity>
    suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): DialogEntity?
}