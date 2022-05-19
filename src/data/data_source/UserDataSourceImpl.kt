package com.chat_server.data.data_source

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.User
import com.chat_server.data.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDataSourceImpl(db: DataBase, usersTable: UsersTable) : UserDataSource(db, usersTable) {
    override suspend fun getUserByUserName(username: String): User? =
        db.dbQuery {
            usersTable.select { usersTable.username.eq(username) }.map { it.mapToUser() }.singleOrNull()
        }

    override suspend fun saveUser(user: User) {
        db.dbQuery {
            usersTable.insert { u ->
                u[usersTable.username] = user.username
                u[usersTable.password] = user.password
            }
        }
    }
}