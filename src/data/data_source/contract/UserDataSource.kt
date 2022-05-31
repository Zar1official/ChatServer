package com.chat_server.data.data_source.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.User
import com.chat_server.data.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow

abstract class UserDataSource(
    protected val db: DataBase,
    protected val usersTable: UsersTable
) {
    abstract suspend fun getUserByUserName(username: String): User?
    abstract suspend fun getUserById(id: Int): User?
    abstract suspend fun saveUser(user: User)
    abstract suspend fun getUsers(): List<User>


    fun ResultRow.mapToUser(): User {
        return User(
            userId = this[usersTable.userId],
            username = this[usersTable.username],
            password = this[usersTable.password]
        )
    }
}