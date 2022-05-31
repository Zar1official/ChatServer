package com.chat_server.controllers

import com.chat_server.controllers.contract.BaseController
import com.chat_server.data.models.User
import com.chat_server.data.repository.contract.Repository
import com.chat_server.exceptions.NoSuchUserException
import com.chat_server.exceptions.UserAlreadyExistsException

class UserController(repository: Repository) : BaseController(repository) {
    suspend fun tryLogin(user: User): User {
        val isValid = repository.login(user)
        if (!isValid) {
            throw NoSuchUserException()
        } else {
            return repository.getUserByUserName(user.username) ?: throw NoSuchUserException()
        }
    }

    suspend fun tryRegister(user: User): User {
        val isValid = repository.register(user)
        if (!isValid)
            throw UserAlreadyExistsException()
        else {
            repository.saveUser(user)
            return repository.getUserByUserName(user.username) ?: throw NoSuchUserException()
        }
    }

    suspend fun getUsers(): List<User> {
        return repository.getUsers()
    }
}