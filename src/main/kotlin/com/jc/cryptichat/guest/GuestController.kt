package com.jc.cryptichat.guest

import com.jc.cryptichat.security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@RequestMapping("guest")
@CrossOrigin
class GuestController{

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping
    fun createGuest(
        @RequestBody uestRequest: GuestRequestDto
    ): GuestCredential {
        val token = tokenService.generateToken(
            mapOf(
                "name" to uestRequest.guestname
            )
        )

        return GuestCredential(
            token = token ?: throw Exception("Failed to generate token")
        )
    }


}