package com.chat_server.data.data_source

import com.chat_server.data.data_source.contract.DialogDataSource
import com.chat_server.data.db.DataBase
import com.chat_server.data.models.Dialog
import com.chat_server.data.models.DialogMessage
import com.chat_server.data.tables.DialogMessagesTable
import com.chat_server.data.tables.DialogsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

class DialogDataSourceImpl(db: DataBase, dialogMessagesTable: DialogMessagesTable, dialogsTable: DialogsTable) :
    DialogDataSource(db, dialogMessagesTable, dialogsTable) {
    override suspend fun getAllDialogMessages(dialogId: Int): List<DialogMessage> {
        return db.dbQuery {
            dialogMessagesTable.select { dialogMessagesTable.dialogId.eq(dialogId) }.map { it.mapToMessage() }.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun saveDialogMessage(dialogMessage: DialogMessage) {
        return db.dbQuery {
            dialogMessagesTable.insert { m ->
                m[dialogMessagesTable.senderUserName] = dialogMessage.sender
                m[dialogMessagesTable.dialogId] = dialogMessage.dialogId
                m[dialogMessagesTable.text] = dialogMessage.text
                m[dialogMessagesTable.timestamp] = dialogMessage.timestamp
            }
        }
    }

    override suspend fun saveDialog(dialog: Dialog) {
        db.dbQuery {
            dialogsTable.insert { d ->
                d[dialogsTable.creatorId] = dialog.creatorId
                d[dialogsTable.companionId] = dialog.companionId
            }
        }
    }

    override suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): Dialog? {
        return db.dbQuery {
            dialogsTable.select {
                (dialogsTable.creatorId.eq(userId) and dialogsTable.companionId.eq(companionId)) or
                        (dialogsTable.creatorId.eq(companionId) and dialogsTable.companionId.eq(userId))
            }.map { it.mapToDialog() }.singleOrNull()
        }
    }

    override suspend fun getUserDialogs(userId: Int): List<Dialog> {
        return db.dbQuery {
            dialogsTable.select {
                dialogsTable.creatorId.eq(userId) or dialogsTable.companionId.eq(userId)
            }.map { it.mapToDialog() }
        }
    }
}