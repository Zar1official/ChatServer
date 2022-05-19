package com.chat_server.data.data_source

import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.UsersTable
import com.chat_server.data.models.User
import org.jetbrains.exposed.sql.ResultRow

abstract class UserDataSource(protected val db: DataBase, protected val usersTable: UsersTable) {
    abstract suspend fun getUserByUserName(username: String): User?
    abstract suspend fun saveUser(user: User)

    fun ResultRow.mapToUser(): User {
        return User(
            username = this[usersTable.username],
            password = this[usersTable.password]
        )
    }
}