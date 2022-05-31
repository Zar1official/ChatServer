package com.chat_server.data.db

import com.chat_server.data.tables.GeneralChatMessagesTable
import com.chat_server.data.tables.UsersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DataBase(private val usersTable: UsersTable, private val generalChatMessagesTable: GeneralChatMessagesTable) {
    init {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(usersTable)
            SchemaUtils.create(generalChatMessagesTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = ""
        config.username = ""
        config.password = ""
        config.maximumPoolSize = 10
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                block()
            }
        }
}