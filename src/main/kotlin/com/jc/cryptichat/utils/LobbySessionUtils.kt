package com.jc.cryptichat.utils

import java.util.concurrent.ConcurrentHashMap

/**
 * Minimalistic in-memory store for rooms. To replace by redis
 */
@Deprecated("To be replaced by redis")
object LobbySessionUtils {
    val clientSession: MutableMap<String, String> = ConcurrentHashMap()

    fun put(id: String, session: String) {
        clientSession[id] = session
    }

    fun get(id: String): String? {
        return clientSession[id]
    }
}