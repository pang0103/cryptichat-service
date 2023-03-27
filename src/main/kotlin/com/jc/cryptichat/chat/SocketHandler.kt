package com.jc.cryptichat.chat

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.annotation.OnConnect
import com.corundumstudio.socketio.annotation.OnDisconnect
import com.corundumstudio.socketio.annotation.OnEvent
import com.jc.cryptichat.chat.dto.*
import org.springframework.stereotype.Component

@Component
class SocketHandler(
    private val socketIOServer: com.corundumstudio.socketio.SocketIOServer
) {

    private val LOG = org.slf4j.LoggerFactory.getLogger(com.jc.cryptichat.chat.SocketHandler::class.java)

    @OnConnect
    fun onConnect(client: SocketIOClient) {
        client.joinRoom("room1")
        LOG.info("Client connected: ${client.remoteAddress}")
    }

    @OnDisconnect
    fun onDisconnect(client: SocketIOClient) {
        client.allRooms.forEach{
            LOG.info(
                "Client disconnected: ${client.remoteAddress}, room: $it"
            )
        }

        LOG.info("Client disconnected: ${client.remoteAddress}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.CREATE_LOBBY)
    fun onCreateLobby(client: SocketIOClient, data: ChatRoomJoinRequest) {
        client.joinRoom(data.code)
        LOG.info("Event lobby join ---> Client ${client.sessionId} joined room: ${data.code}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.JOIN_LOBBY_REQUEST)
    fun onJoinLobbyRequest(client: SocketIOClient, data: LobbyJoinRequestDto) {
        socketIOServer
            .getRoomOperations(data.room)
            .sendEvent(com.jc.cryptichat.chat.ChatEvent.PEER_REQUEST_JOIN_LOBBY, data)
        LOG.info("Event lobby join ---> Client ${client.sessionId} requested to join room: ${data.room}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.JOIN_LOBBY_RESPONSE)
    fun onJoinLobbyResponse(client: SocketIOClient, data: LobbyJoinResponseDto) {
        socketIOServer
            .getRoomOperations(data.room)
            .sendEvent(com.jc.cryptichat.chat.ChatEvent.PEER_RESPONSE_JOIN_LOBBY, data)
        LOG.info("Event lobby join ---> Client ${client.sessionId} responded to join room: ${data.room}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.LOBBY_LEAVE)
    fun onLobbyLeave(client: SocketIOClient, data: ChatRoomJoinRequest) {
        client.leaveRoom(data.code)
        LOG.info("Event lobby leave ---> Client ${client.sessionId} left room: ${data.code}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.CHATROOM_JOIN_ACCEPT)
    fun onChatRoomJoin(client: SocketIOClient, data: ChatRoomJoinRequest) {
        client.joinRoom(data.code)
        LOG.info("Event chatroom join ---> Client ${client.sessionId} joined room: ${data.code}")
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.CHAT_MESSAGE_OUTGOING)
    fun onSendMessage(client: SocketIOClient, data: ChatMessage) {
        LOG.info("Event chat message ---> Client ${client.sessionId} sent message: ${data.content}")
        socketIOServer.getRoomOperations(data.room).sendEvent(com.jc.cryptichat.chat.ChatEvent.CHAT_MESSAGE_INCOMING, data.content)
    }

    @OnEvent(com.jc.cryptichat.chat.ChatEvent.USER_TYPING_SEND)
    fun onUserTyping(client: SocketIOClient, data: TypingEventDto) {
        LOG.info("Event user typing ---> Client ${client.sessionId} is typing in room: ${data}")
        client.sessionId
        socketIOServer.getRoomOperations(data.room).sendEvent(com.jc.cryptichat.chat.ChatEvent.USER_TYPING_RECEIVE, data)
    }

    @OnEvent("chat")
    fun onEvent(client: SocketIOClient, data: String) {
        println("Event chat ---> client session id: ${client.sessionId}")
        println("Event chat ---> data: $data")
        socketIOServer.allClients.forEach {
            it.sendEvent("chat", data)
        }
    }

}