package com.oussama_chatri.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val SECRET_KEY = "oussama_chatri.secretkey"
    private const val ISSUER = "http://127.0.0.1:8080/"
    private const val EXPIRATION_TIME = 36_000_00 * 24 * 7
    private const val Audience = "http://127.0.0.1:8080/productivity"

    private val algorithm = Algorithm.HMAC256(SECRET_KEY)

    fun generateToken(userId: String): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim("userId", userId)
            .withAudience(Audience)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(algorithm)
    }

    fun getVerifier() = JWT.require(algorithm).withIssuer(ISSUER).build()
}