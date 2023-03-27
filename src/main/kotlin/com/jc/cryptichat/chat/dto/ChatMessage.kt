package com.jc.cryptichat.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatMessage(
    @JsonProperty("room") val room: String,
    @JsonProperty("content") val content: ChatMessageBody
)

data class ChatMessageBody (
    @JsonProperty("author") val author: String,
    @JsonProperty("message") val message: String,
    @JsonProperty("timestamp") val timestamp: String
)