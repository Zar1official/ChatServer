package com.chat_server.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class MessagesTable : Table("messages") {
    val senderUserName: Column<String> = varchar("sender", 100)
    val text: Column<String> = varchar("text", 200)
    val timestamp: Column<Long> = long("timestamp")

    override val primaryKey: Table.PrimaryKey = PrimaryKey(timestamp)
}