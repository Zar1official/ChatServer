package com.chat_server.data.repository

import com.chat_server.data.datasource.contract.ChatDataSource
import com.chat_server.data.datasource.contract.DialogDataSource
import com.chat_server.data.datasource.contract.UserDataSource
import com.chat_server.data.models.*
import com.chat_server.data.repository.contract.Repository
import models.DialogEntity
import models.DialogMessageEntity
import models.GeneralChatMessageEntity
import models.UserEntity

class RepositoryImpl(
    private val chatDataSource: ChatDataSource,
    private val userDataSource: UserDataSource,
    private val dialogDataSource: DialogDataSource
) :
    Repository {

    override suspend fun register(userEntity: UserEntity): Boolean {
        val currentUser = userDataSource.getUserByUserName(userEntity.username)
        if (currentUser != null) {
            return false
        }
        return true
    }

    override suspend fun login(userEntity: UserEntity): Boolean {
        val currentUser = userDataSource.getUserByUserName(userEntity.username)
        if (currentUser == null || currentUser.password != userEntity.password) {
            return false
        }
        return true
    }

    override suspend fun saveUser(userEntity: UserEntity) {
        userDataSource.saveUser(userEntity)
    }

    override suspend fun getUserByUserName(username: String): UserEntity? {
        return userDataSource.getUserByUserName(username)
    }

    override suspend fun getUsers(): List<UserEntity> {
        return userDataSource.getUsers()
    }

    override suspend fun getGeneralChatMessages(): List<GeneralChatMessageEntity> {
        return chatDataSource.getAllGeneralChatMessages()
    }

    override suspend fun saveGeneralChatMessage(messageEntity: GeneralChatMessageEntity) {
        chatDataSource.saveGeneralChatMessage(messageEntity)
    }

    override suspend fun saveDialog(dialogEntity: DialogEntity) {
        dialogDataSource.saveDialog(dialogEntity)
    }

    override suspend fun saveDialogMessage(messageEntity: DialogMessageEntity) {
        dialogDataSource.saveDialogMessage(messageEntity)
    }

    override suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity> {
        return dialogDataSource.getAllDialogMessages(dialogId)
    }

    override suspend fun getDialogs(userId: Int): List<DialogEntity> {
        return dialogDataSource.getUserDialogs(userId)
    }

    override suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): DialogEntity? {
        return dialogDataSource.getDialogBetweenUsers(userId, companionId)
    }
}