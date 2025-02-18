package com.oussama_chatri.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object JwtConfig {
    private const val SECRET_KEY = "oussama_chatri.secretkey"
    private const val ISSUER = "http://127.0.0.1:8080/"
    private const val Audience = "http://127.0.0.1:8080/productivity"

    private val algorithm = Algorithm.HMAC256(SECRET_KEY)

    fun generateToken(userId: String): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim("user_id", userId)
            .withAudience(Audience)
            .sign(algorithm)
    }

    fun getVerifier() = JWT.require(algorithm).withIssuer(ISSUER).build()
}