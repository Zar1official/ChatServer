package com.chat_server.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class UsersTable : Table("users") {
    val userId: Column<Int> = integer("user_id")
    val username: Column<String> = varchar("username", 25)
    val password: Column<String> = varchar("password", 25)

    override val primaryKey: PrimaryKey = PrimaryKey(userId)
}