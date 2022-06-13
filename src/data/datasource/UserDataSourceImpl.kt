package com.chat_server.data.datasource

import com.chat_server.data.datasource.contract.UserDataSource
import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.UsersTable
import models.UserEntity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class UserDataSourceImpl(db: DataBase, usersTable: UsersTable) :
    UserDataSource(db, usersTable) {
    override suspend fun getUserByUserName(username: String): UserEntity? =
        db.dbQuery {
            usersTable.select { usersTable.username.eq(username) }.map { it.mapToUser() }.singleOrNull()
        }

    override suspend fun getUserById(id: Int): UserEntity? =
        db.dbQuery {
            usersTable.select { usersTable.userId.eq(id) }.map { it.mapToUser() }.singleOrNull()
        }

    override suspend fun saveUser(userEntity: UserEntity) {
        db.dbQuery {
            usersTable.insert { u ->
                u[usersTable.username] = userEntity.username
                u[usersTable.password] = userEntity.password
            }
        }
    }

    override suspend fun getUsers(): List<UserEntity> {
        return db.dbQuery {
            usersTable.selectAll().map { it.mapToUser() }
        }
    }
}