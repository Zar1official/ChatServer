package com.chat_server.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String)