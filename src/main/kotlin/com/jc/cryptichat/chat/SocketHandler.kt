package com.jc.cryptichat.chat

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.annotation.OnConnect
import com.corundumstudio.socketio.annotation.OnDisconnect
import com.corundumstudio.socketio.annotation.OnEvent
import com.jc.cryptichat.chat.dto.*
import com.jc.cryptichat.utils.LobbySessionUtils
import com.jc.cryptichat.utils.UserSessionUtils
import com.jc.cryptichat.utils.UserSession
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SocketHandler(
    private val socketIOServer: com.corundumstudio.socketio.SocketIOServer
) {

    private val LOG = LoggerFactory.getLogger(SocketHandler::class.java)

    @OnConnect
    fun onConnect(client: SocketIOClient) {
        UserSessionUtils.put(client.sessionId.toString(), UserSession(
            id = client.sessionId.toString(),
        ))
        LOG.info("Client connected: ${client.remoteAddress}")
    }

    @OnDisconnect
    fun onDisconnect(client: SocketIOClient) {
        UserSessionUtils.get(client.sessionId.toString())?.let {
            socketIOServer.getRoomOperations(it.roomId).sendEvent(
                ChatEvent.DISCONNECT_ANNOUNCEMENT,
                DisconnectNoticeDto(
                    author = it.username ?: "Unknown",
                    reason = "Client disconnected"
                )
            )
        }
        UserSessionUtils.remove(client.sessionId.toString())
        LOG.info("Client disconnected: ${client.remoteAddress}")
    }

    @OnEvent(ChatEvent.CREATE_LOBBY)
    fun onCreateLobby(client: SocketIOClient, data: ChatRoomJoinRequest) {
        client.joinRoom(data.code)
        UserSessionUtils.get(client.sessionId.toString())?.let {
            it.username = data.user
            it.lobbyId = data.code
            UserSessionUtils.put(client.sessionId.toString(), it)
        }
        LOG.info("Event lobby join ---> Client ${client.sessionId} joined room: ${data.code}")
    }

    @OnEvent(ChatEvent.JOIN_LOBBY_REQUEST)
    fun onJoinLobbyRequest(client: SocketIOClient, data: LobbyJoinRequestDto) {
        socketIOServer
            .getRoomOperations(data.room)
            .sendEvent(ChatEvent.PEER_REQUEST_JOIN_LOBBY, data)
        LOG.info("Event lobby join ---> Client ${client.sessionId} requested to join room: ${data.room}")
    }

    @OnEvent(ChatEvent.JOIN_LOBBY_RESPONSE)
    fun onJoinLobbyResponse(client: SocketIOClient, data: LobbyJoinResponseDto) {
        socketIOServer
            .getRoomOperations(data.room)
            .sendEvent(ChatEvent.PEER_RESPONSE_JOIN_LOBBY, data)
        LOG.info("Event lobby join ---> Client ${client.sessionId} responded to join room: ${data.room}")
    }

    @OnEvent(ChatEvent.LOBBY_LEAVE)
    fun onLobbyLeave(client: SocketIOClient, data: ChatRoomJoinRequest) {
        client.leaveRoom(data.code)
        LOG.info("Event lobby leave ---> Client ${client.sessionId} left room: ${data.code}")
    }

    @OnEvent(ChatEvent.CHATROOM_JOIN_ACCEPT)
    fun onChatRoomJoin(client: SocketIOClient, data: ChatRoomJoinRequest) {
        val roomId = LobbySessionUtils.get(data.code)
        if (roomId == null) {
            LOG.error("Room not found: ${data.code}")
        } else {
            client.joinRoom(roomId)
            UserSessionUtils.get(client.sessionId.toString())?.let {
                it.roomId = roomId
                it.username = data.user
                UserSessionUtils.put(client.sessionId.toString(), it)
            }

            LOG.info("Event chatroom join ---> Client ${client.sessionId} joined room: $roomId")
        }
    }

    @OnEvent(ChatEvent.CHAT_MESSAGE_OUTGOING)
    fun onSendMessage(client: SocketIOClient, data: ChatMessage) {
        LOG.trace("Event chat message ---> Client ${client.sessionId} sent message: ${data.content}")
        socketIOServer.getRoomOperations(data.room).sendEvent(ChatEvent.CHAT_MESSAGE_INCOMING, data.content)
    }

    @OnEvent(ChatEvent.USER_TYPING_SEND)
    fun onUserTyping(client: SocketIOClient, data: TypingEventDto) {
        LOG.trace("Event user typing ---> Client ${client.sessionId} is typing in room: ${data}")
        client.sessionId
        socketIOServer.getRoomOperations(data.room).sendEvent(ChatEvent.USER_TYPING_RECEIVE, data)
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