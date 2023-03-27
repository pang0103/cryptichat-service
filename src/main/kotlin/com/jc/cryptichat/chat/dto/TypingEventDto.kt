package com.jc.cryptichat.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TypingEventDto (
    @JsonProperty("room") val room: String,
    @JsonProperty("author") val author: String
)
