package com.chat_server.data.data_source.contract

import com.chat_server.data.db.DataBase
import com.chat_server.data.models.Dialog
import com.chat_server.data.models.DialogMessage
import com.chat_server.data.tables.DialogMessagesTable
import com.chat_server.data.tables.DialogsTable
import org.jetbrains.exposed.sql.ResultRow

abstract class DialogDataSource(
    protected val db: DataBase,
    protected val dialogMessagesTable: DialogMessagesTable,
    protected val dialogsTable: DialogsTable
) {
    abstract suspend fun getAllDialogMessages(dialogId: Int): List<DialogMessage>
    abstract suspend fun saveDialogMessage(dialogMessage: DialogMessage)
    abstract suspend fun saveDialog(dialog: Dialog)
    abstract suspend fun getDialogBetweenUsers(userId: Int, companionId: Int): Dialog?
    abstract suspend fun getUserDialogs(userId: Int): List<Dialog>

    fun ResultRow.mapToMessage(): DialogMessage {
        return DialogMessage(
            messageId = this[dialogMessagesTable.messageId],
            dialogId = this[dialogMessagesTable.dialogId],
            text = this[dialogMessagesTable.text],
            timestamp = this[dialogMessagesTable.timestamp],
            sender = this[dialogMessagesTable.senderUserName]
        )
    }

    fun ResultRow.mapToDialog(): Dialog {
        return Dialog(
            dialogId = this[dialogsTable.dialogId],
            creatorId = this[dialogsTable.creatorId],
            companionId = this[dialogsTable.companionId]
        )
    }
}