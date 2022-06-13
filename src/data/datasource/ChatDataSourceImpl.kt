package com.chat_server.data.datasource

import com.chat_server.data.datasource.contract.ChatDataSource
import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.GeneralChatMessagesTable
import models.GeneralChatMessageEntity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ChatDataSourceImpl(db: DataBase, generalChatMessagesTable: GeneralChatMessagesTable) : ChatDataSource(db, generalChatMessagesTable) {
    override suspend fun getAllGeneralChatMessages(): List<GeneralChatMessageEntity> {
        return db.dbQuery {
            generalChatMessagesTable.selectAll().map { it.mapToMessage() }.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun getGeneralChatMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessageEntity> {
        return db.dbQuery {
            generalChatMessagesTable.select {
                generalChatMessagesTable.timestamp.greater(timestamp)
            }.map { it.mapToMessage() }
        }
    }

    override suspend fun saveGeneralChatMessage(generalChatMessageEntity: GeneralChatMessageEntity) {
        db.dbQuery {
            generalChatMessagesTable.insert { m ->
                m[generalChatMessagesTable.senderUserName] = generalChatMessageEntity.senderUserName
                m[generalChatMessagesTable.text] = generalChatMessageEntity.text
                m[generalChatMessagesTable.timestamp] = generalChatMessageEntity.timestamp
            }
        }
    }
}