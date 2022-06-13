package com.chat_server.data.datasource.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.UsersTable
import models.UserEntity
import org.jetbrains.exposed.sql.ResultRow

abstract class UserDataSource(
    protected val db: DataBase,
    protected val usersTable: UsersTable
) {
    abstract suspend fun getUserByUserName(username: String): UserEntity?
    abstract suspend fun getUserById(id: Int): UserEntity?
    abstract suspend fun saveUser(userEntity: UserEntity)
    abstract suspend fun getUsers(): List<UserEntity>


    fun ResultRow.mapToUser(): UserEntity {
        return UserEntity(
            userId = this[usersTable.userId],
            username = this[usersTable.username],
            password = this[usersTable.password]
        )
    }
}