package com.jc.cryptichat.utils

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Minimalistic in-memory store for rooms. To replace by redis
 */
object UserSessionUtils {
    private val socketSession: ConcurrentMap<String, UserSession> = ConcurrentHashMap()

    fun put(id: String, session: UserSession) {
        socketSession[id] = session
    }

    fun get(id: String): UserSession? {
        return socketSession[id]
    }

    fun remove(id: String) {
        socketSession.remove(id)
    }
}