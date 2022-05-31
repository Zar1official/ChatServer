package com.chat_server.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class DialogMessagesTable: Table("dialog_messages") {
    val messageId: Column<Int> = integer("message_id")
    val dialogId: Column<Int> = integer("dialog_id")
    val senderUserName: Column<String> = varchar("sender", 25)
    val text: Column<String> = varchar("text", 500)
    val timestamp: Column<Long> = long("timestamp")

    override val primaryKey: Table.PrimaryKey = PrimaryKey(messageId)
}