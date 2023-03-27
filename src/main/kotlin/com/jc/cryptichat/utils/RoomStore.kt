package com.jc.cryptichat.utils

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Minimalistic in-memory store for rooms. To replace by redis
 */
object SessionUtils {
    val clientSession: ConcurrentMap<String, String> = ConcurrentHashMap()

    fun put(id: String, roomId: String) {
        clientSession[id] = roomId
    }

    fun get(id: String): String? {
        return clientSession[id]
    }

}