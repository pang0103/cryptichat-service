package com.jc.cryptichat.chat.dto

data class LobbyJoinRequestDto(
    val room: String ="",
    val user: String = "",
    val message: String= "",
    val accepted: Boolean = false
)
