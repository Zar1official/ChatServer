package com.chat_server.data.data_source

import com.chat_server.data.data_source.contract.ChatDataSource
import com.chat_server.data.db.DataBase
import com.chat_server.data.models.GeneralChatMessage
import com.chat_server.data.tables.GeneralChatMessagesTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ChatDataSourceImpl(db: DataBase, generalChatMessagesTable: GeneralChatMessagesTable) : ChatDataSource(db, generalChatMessagesTable) {
    override suspend fun getAllGeneralChatMessages(): List<GeneralChatMessage> {
        return db.dbQuery {
            generalChatMessagesTable.selectAll().map { it.mapToMessage() }.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun getGeneralChatMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessage> {
        return db.dbQuery {
            generalChatMessagesTable.select {
                generalChatMessagesTable.timestamp.greater(timestamp)
            }.map { it.mapToMessage() }
        }
    }

    override suspend fun saveGeneralChatMessage(generalChatMessage: GeneralChatMessage) {
        db.dbQuery {
            generalChatMessagesTable.insert { m ->
                m[generalChatMessagesTable.senderUserName] = generalChatMessage.senderUserName
                m[generalChatMessagesTable.text] = generalChatMessage.text
                m[generalChatMessagesTable.timestamp] = generalChatMessage.timestamp
            }
        }
    }
}