package com.chat_server.controllers

import com.chat_server.controllers.contract.BaseController
import com.chat_server.data.repository.contract.Repository
import exceptions.NoSuchUserException
import exceptions.UserAlreadyExistsException
import models.UserEntity

class UserController(repository: Repository) : BaseController(repository) {
    suspend fun tryLogin(userEntity: UserEntity): UserEntity {
        val isValid = repository.login(userEntity)
        if (!isValid) {
            throw NoSuchUserException()
        } else {
            return repository.getUserByUserName(userEntity.username) ?: throw NoSuchUserException()
        }
    }

    suspend fun tryRegister(userEntity: UserEntity): UserEntity {
        val isValid = repository.register(userEntity)
        if (!isValid)
            throw UserAlreadyExistsException()
        else {
            repository.saveUser(userEntity)
            return repository.getUserByUserName(userEntity.username) ?: throw NoSuchUserException()
        }
    }

    suspend fun getUsers(): List<UserEntity> {
        return repository.getUsers()
    }
}