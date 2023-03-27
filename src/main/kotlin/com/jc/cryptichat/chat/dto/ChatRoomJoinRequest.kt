package com.jc.cryptichat.chat.dto

data class ChatRoomJoinRequest(
    val user: String = "",
    val code: String = ""
)