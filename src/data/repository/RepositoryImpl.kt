package com.chat_server.data.repository

import com.chat_server.data.data_source.contract.ChatDataSource
import com.chat_server.data.data_source.contract.DialogDataSource
import com.chat_server.data.data_source.contract.UserDataSource
import com.chat_server.data.models.*
import com.chat_server.data.repository.contract.Repository

class RepositoryImpl(
    private val chatDataSource: ChatDataSource,
    private val userDataSource: UserDataSource,
    private val dialogDataSource: DialogDataSource
) :
    Repository {

    override suspend fun register(user: User): Boolean {
        val currentUser = userDataSource.getUserByUserName(user.username)
        if (currentUser != null) {
            return false
        }
        return true
    }

    override suspend fun login(user: User): Boolean {
        val currentUser = userDataSource.getUserByUserName(user.username)
        if (currentUser == null || currentUser.password != user.password) {
            return false
        }
        return true
    }

    override suspend fun saveUser(user: User) {
        userDataSource.saveUser(user)
    }

    override suspend fun getUserByUserName(username: String): User? {
        return userDataSource.getUserByUserName(username)
    }

    override suspend fun getUsers(): List<User> {
        return userDataSource.getUsers()
    }

    override suspend fun getGeneralChatMessages(): List<GeneralChatMessage> {
        return chatDataSource.getAllGeneralChatMessages()
    }

    override suspend fun saveGeneralChatMessage(message: GeneralChatMessage) {
        chatDataSource.saveGeneralChatMessage(message)
    }

    override suspend fun saveDialog(dialog: Dialog) {
        dialogDataSource.saveDialog(dialog)
    }

    override suspend fun saveDialogMessage(message: DialogMessage) {
        dialogDataSource.saveDialogMessage(message)
    }

    override suspend fun getDialogMessages(dialogId: Int): List<DialogMessage> {
        return dialogDataSource.getAllDialogMessages(dialogId)
    }

    override suspend fun getDialogs(userId: Int): List<Dialog> {
        return dialogDataSource.getUserDialogs(userId)
    }

    override suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): Dialog? {
        return dialogDataSource.getDialogBetweenUsers(userId, companionId)
    }
}