package com.chat_server.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class DialogsTable: Table("dialogs") {
    val dialogId: Column<Int> = integer("dialog_id")
    val creatorId: Column<Int> = integer("creator_id")
    val companionId: Column<Int> = integer("companion_id")

    override val primaryKey: Table.PrimaryKey = PrimaryKey(dialogId)
}