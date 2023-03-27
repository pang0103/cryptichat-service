package com.jc.cryptichat.utils

data class RoomSession(
    val id: String,
    val code: String,
    val roomId: String,
    val initAt: Long,
    val expireAt: Long,
)