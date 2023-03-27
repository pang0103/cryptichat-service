package com.jc.cryptichat.security

import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class TokenService {

    val jwtSecret = "someExampleJwtSecretsomeExampleJwtSecretsomeExampleJwtSecret"

    fun generateToken(jwtPayload: Map<String, String>): String? {
        val now = Instant.now()
        val claimsSet = JWTClaimsSet.Builder()
            .issuer("self")
            .issueTime(Date.from(now))
            .expirationTime(Date.from(now.plusSeconds(60 * 60)))
            .claim("name", jwtPayload["name"])
            .build()

        val jwsHeader = JWSHeader(JWSAlgorithm.HS256)
        val jwsObject = JWSObject(
            jwsHeader, Payload(
                claimsSet.toJSONObject()
            )
        )
        val signer = MACSigner(jwtSecret.toByteArray())
        try {
            jwsObject.sign(signer)
        } catch (e: JOSEException) {
            // Handle signing exception
            throw RuntimeException("Failed to sign JWT", e)
        }

        return jwsObject.serialize()
    }
}