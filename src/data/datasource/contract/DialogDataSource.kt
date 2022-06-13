package com.chat_server.data.datasource.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.DialogMessagesTable
import com.chat_server.data.tables.DialogsTable
import models.DialogEntity
import models.DialogMessageEntity
import org.jetbrains.exposed.sql.ResultRow

abstract class DialogDataSource(
    protected val db: DataBase,
    protected val dialogMessagesTable: DialogMessagesTable,
    protected val dialogsTable: DialogsTable
) {
    abstract suspend fun getAllDialogMessages(dialogId: Int): List<DialogMessageEntity>
    abstract suspend fun saveDialogMessage(dialogMessageEntity: DialogMessageEntity)
    abstract suspend fun saveDialog(dialogEntity: DialogEntity)
    abstract suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): DialogEntity?
    abstract suspend fun getUserDialogs(userId: Int): List<DialogEntity>

    fun ResultRow.mapToMessage(): DialogMessageEntity {
        return DialogMessageEntity(
            messageId = this[dialogMessagesTable.messageId],
            dialogId = this[dialogMessagesTable.dialogId],
            text = this[dialogMessagesTable.text],
            timestamp = this[dialogMessagesTable.timestamp],
            sender = this[dialogMessagesTable.senderUserName]
        )
    }

    fun ResultRow.mapToDialog(): DialogEntity {
        return DialogEntity(
            dialogId = this[dialogsTable.dialogId],
            creatorId = this[dialogsTable.creatorId],
            companionId = this[dialogsTable.companionId]
        )
    }
}