package com.chat_server.data.datasource.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.GeneralChatMessagesTable
import models.GeneralChatMessageEntity
import org.jetbrains.exposed.sql.ResultRow

abstract class ChatDataSource(protected val db: DataBase, protected val generalChatMessagesTable: GeneralChatMessagesTable) {
    abstract suspend fun getAllGeneralChatMessages(): List<GeneralChatMessageEntity>
    abstract suspend fun getGeneralChatMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessageEntity>
    abstract suspend fun saveGeneralChatMessage(generalChatMessageEntity: GeneralChatMessageEntity)

    fun ResultRow.mapToMessage(): GeneralChatMessageEntity {
        return GeneralChatMessageEntity(
            senderUserName = this[generalChatMessagesTable.senderUserName],
            text = this[generalChatMessagesTable.text],
            timestamp = this[generalChatMessagesTable.timestamp],
            messageId = this[generalChatMessagesTable.messageId]
        )
    }
}