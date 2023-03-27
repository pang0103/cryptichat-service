package com.jc.cryptichat.config

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SocketIOConfig {

    @Value("\${socketio.host}")
    private lateinit var host: String

    @Value("\${socketio.port}")
    private lateinit var port: String


    @Bean
    fun socketIOServer(): SocketIOServer {
        val config = com.corundumstudio.socketio.Configuration()
        config.hostname = host
        config.port = port.toInt()
        config.context = "/socket.io"
        return SocketIOServer(config)
    }

    @Bean
    fun springAnnotationScanner(socketIOServer: SocketIOServer): SpringAnnotationScanner {
        return SpringAnnotationScanner(socketIOServer)
    }
}