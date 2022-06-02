package com.chat_server.data.data_source.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.GeneralChatMessage
import com.chat_server.data.tables.GeneralChatMessagesTable
import org.jetbrains.exposed.sql.ResultRow

abstract class ChatDataSource(protected val db: DataBase, protected val generalChatMessagesTable: GeneralChatMessagesTable) {
    abstract suspend fun getAllGeneralChatMessages(): List<GeneralChatMessage>
    abstract suspend fun getGeneralChatMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessage>
    abstract suspend fun saveGeneralChatMessage(generalChatMessage: GeneralChatMessage)

    fun ResultRow.mapToMessage(): GeneralChatMessage {
        return GeneralChatMessage(
            senderUserName = this[generalChatMessagesTable.senderUserName],
            text = this[generalChatMessagesTable.text],
            timestamp = this[generalChatMessagesTable.timestamp],
            messageId = this[generalChatMessagesTable.messageId]
        )
    }
}