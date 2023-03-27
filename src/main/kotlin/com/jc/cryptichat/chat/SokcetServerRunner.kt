package com.jc.cryptichat.chat

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SocketServerRunner: ApplicationRunner {

    @Autowired
    private lateinit var server: SocketIOServer


    override fun run(args: ApplicationArguments?) {
        server.start()
    }
}