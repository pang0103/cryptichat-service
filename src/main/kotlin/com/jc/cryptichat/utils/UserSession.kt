package com.jc.cryptichat.utils

data class UserSession(
    val id: String,
    var username: String? = null,
    var lobbyId: String? = null,
    var roomId: String? = null,
)