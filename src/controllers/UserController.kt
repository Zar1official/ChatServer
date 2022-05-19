package com.chat_server.controllers

import com.chat_server.data.data_source.UserDataSource
import com.chat_server.data.models.User
import com.chat_server.exceptions.NoSuchUserException
import com.chat_server.exceptions.UserAlreadyExistsException

class UserController(private val userDataSource: UserDataSource) {
    suspend fun tryLogin(user: User) {
        val currentUser = userDataSource.getUserByUserName(user.username)
        if (currentUser == null || currentUser.password != user.password) {
            throw NoSuchUserException()
        }
    }

    suspend fun tryRegister(user: User) {
        val currentUser = userDataSource.getUserByUserName(user.username)
        if (currentUser != null)
            throw UserAlreadyExistsException()
        userDataSource.saveUser(user)
    }
}