package com.chat_server.data.datasource

import com.chat_server.data.datasource.contract.DialogDataSource
import com.chat_server.data.db.DataBase
import com.chat_server.data.tables.DialogMessagesTable
import com.chat_server.data.tables.DialogsTable
import models.DialogEntity
import models.DialogMessageEntity
import org.jetbrains.exposed.sql.*

class DialogDataSourceImpl(db: DataBase, dialogMessagesTable: DialogMessagesTable, dialogsTable: DialogsTable) :
    DialogDataSource(db, dialogMessagesTable, dialogsTable) {
    override suspend fun getAllDialogMessages(dialogId: Int): List<DialogMessageEntity> {
        return db.dbQuery {
            dialogMessagesTable.select { dialogMessagesTable.dialogId.eq(dialogId) }.map<ResultRow, DialogMessageEntity> { it.mapToMessage() }.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun saveDialogMessage(dialogMessageEntity: DialogMessageEntity) {
        return db.dbQuery {
            dialogMessagesTable.insert { m ->
                m[dialogMessagesTable.senderUserName] = dialogMessageEntity.sender
                m[dialogMessagesTable.dialogId] = dialogMessageEntity.dialogId
                m[dialogMessagesTable.text] = dialogMessageEntity.text
                m[dialogMessagesTable.timestamp] = dialogMessageEntity.timestamp
            }
        }
    }

    override suspend fun saveDialog(dialogEntity: DialogEntity) {
        db.dbQuery {
            dialogsTable.insert { d ->
                d[dialogsTable.creatorId] = dialogEntity.creatorId
                d[dialogsTable.companionId] = dialogEntity.companionId
            }
        }
    }

    override suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): DialogEntity? {
        return db.dbQuery {
            dialogsTable.select {
                (dialogsTable.creatorId.eq(userId) and dialogsTable.companionId.eq(companionId)) or
                        (dialogsTable.creatorId.eq(companionId) and dialogsTable.companionId.eq(userId))
            }.map { it.mapToDialog() }.singleOrNull()
        }
    }

    override suspend fun getUserDialogs(userId: Int): List<DialogEntity> {
        return db.dbQuery {
            dialogsTable.select {
                dialogsTable.creatorId.eq(userId) or dialogsTable.companionId.eq(userId)
            }.map { it.mapToDialog() }
        }
    }
}