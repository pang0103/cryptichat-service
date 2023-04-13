package com.jc.cryptichat.chat

object ClientChatEvent  {

}

object ChatEvent {
    const val CHAT = "chat"

    const val CREATE_LOBBY = "lobby_join" //client create a lobby
    const val JOIN_LOBBY_REQUEST = "join_request" //client request to join a lobby
    const val JOIN_LOBBY_RESPONSE = "join_response" //client response to join a lobby

    const val PEER_REQUEST_JOIN_LOBBY = "peer_request" // client side listen to this event
    const val PEER_RESPONSE_JOIN_LOBBY = "peer_response" //client side listen to this event

    const val LOBBY_LEAVE = "lobby_leave"

    const val CHATROOM_JOIN_ACCEPT = "chatRoom_join"
    /**
     * User chat message
     */
    const val CHAT_MESSAGE_OUTGOING = "send_message"
    const val CHAT_MESSAGE_INCOMING = "receive_message"
    const val CHAT_TYPING = "userTyping"

    /**
     * User behavior
     */
    const val USER_TYPING_SEND = "userTyping"
    const val USER_TYPING_RECEIVE = "receive_userTyping"
    const val DISCONNECT_ANNOUNCEMENT = "dcNotice"
}