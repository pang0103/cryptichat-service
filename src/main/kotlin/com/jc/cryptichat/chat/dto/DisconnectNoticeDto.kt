package com.jc.cryptichat.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DisconnectNoticeDto(
    @JsonProperty("author") val author: String,
    @JsonProperty("reason") val reason: String
)
