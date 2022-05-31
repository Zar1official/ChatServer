package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val userId: Int = 0, val username: String, val password: String)