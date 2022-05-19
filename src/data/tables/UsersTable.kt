package com.chat_server.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class UsersTable : Table("users") {
    val username: Column<String> = varchar("username", 100)
    val password: Column<String> = varchar("password", 100)

    override val primaryKey: PrimaryKey = PrimaryKey(username)
}