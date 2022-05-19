package com.chat_server.data.data_source

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.Message
import com.chat_server.data.tables.MessagesTable
import org.jetbrains.exposed.sql.ResultRow

abstract class ChatDataSource(protected val db: DataBase, protected val messagesTable: MessagesTable) {
    abstract suspend fun getAllMessages(): List<Message>
    abstract suspend fun getMessagesFromTimeStamp(timestamp: Long): List<Message>
    abstract suspend fun saveMessage(message: Message)

    fun ResultRow.mapToMessage(): Message {
        return Message(
            senderUserName = this[messagesTable.senderUserName],
            text = this[messagesTable.text],
            timestamp = this[messagesTable.timestamp]
        )
    }
}