package com.jc.cryptichat.lobby

import com.jc.cryptichat.lobby.dto.CreateLobbyResponseDto
import com.jc.cryptichat.lobby.dto.JoinLobbyRequestDto
import com.jc.cryptichat.lobby.dto.JoinLobbyResponseDto
import com.jc.cryptichat.utils.LobbySessionUtils
import com.jc.cryptichat.utils.UserSessionUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("lobby")
@CrossOrigin
class LobbyController {

    @PostMapping("create")
    fun createLobby(): CreateLobbyResponseDto {
        val code = String.format("%04d", (0..9999).random())
        val roomId = UUID.randomUUID().toString()
        LobbySessionUtils.put(code, roomId)
        return CreateLobbyResponseDto(
            code = code,
        )
    }

    @PostMapping("join")
    fun joinLobby(
        @RequestBody joinLobbyRequestDto: JoinLobbyRequestDto
    ): JoinLobbyResponseDto {
        if (LobbySessionUtils.get(joinLobbyRequestDto.code) == null) return JoinLobbyResponseDto(
            accepted = false,
        )

        return JoinLobbyResponseDto(
            accepted = true,
        )
    }


}