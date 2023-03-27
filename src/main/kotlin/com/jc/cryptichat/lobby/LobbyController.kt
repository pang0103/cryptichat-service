package com.jc.cryptichat.lobby

import com.example.sockettest.lobby.dto.CreateLobbyResponseDto
import com.example.sockettest.lobby.dto.JoinLobbyRequestDto
import com.example.sockettest.lobby.dto.JoinLobbyResponseDto
import com.example.sockettest.utils.SessionUtils
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
        SessionUtils.put(code, roomId)

        return CreateLobbyResponseDto(
            code = code,
        )
    }

    @PostMapping("join")
    fun joinLobby(
        @RequestBody joinLobbyRequestDto: JoinLobbyRequestDto
    ): JoinLobbyResponseDto {
        if (SessionUtils.get(joinLobbyRequestDto.code) == null) return JoinLobbyResponseDto(
            accepted = false,
        )

        return JoinLobbyResponseDto(
            accepted = true,
        )
    }


}