package com.chat_server.data.data_source

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.Message
import com.chat_server.data.tables.MessagesTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ChatDataSourceImpl(db: DataBase, messagesTable: MessagesTable) : ChatDataSource(db, messagesTable) {
    override suspend fun getAllMessages(): List<Message> {
        return db.dbQuery {
            messagesTable.selectAll().map { it.mapToMessage() }.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun getMessagesFromTimeStamp(timestamp: Long): List<Message> {
        return db.dbQuery {
            messagesTable.select {
                messagesTable.timestamp.greater(timestamp)
            }.map { it.mapToMessage() }
        }
    }

    override suspend fun saveMessage(message: Message) {
        db.dbQuery {
            messagesTable.insert { m ->
                m[messagesTable.senderUserName] = message.senderUserName
                m[messagesTable.text] = message.text
                m[messagesTable.timestamp] = message.timestamp
            }
        }
    }
}