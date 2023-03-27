package com.jc.cryptichat.chat.dto

data class LobbyJoinResponseDto (
    val room: String = "",
    val accepted: Boolean = false,
    val message: String = ""
)