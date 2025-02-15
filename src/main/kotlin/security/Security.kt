package com.oussama_chatri.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    val SECRET_KEY = "oussama_chatri.secretkey"
    val ISSUER = "http://127.0.0.1:8080/"
    val EXPIRATION_TIME = 36_000_00 * 24 * 7
    val Audience = "http://127.0.0.1:8080/productivity"

    authentication {
        jwt("auth-jwt") {
            realm = "ktor app"
            verifier(
                JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .withAudience(Audience)
                    .withIssuer(ISSUER)
                    .build()
            )
            validate { credential ->
                credential.payload.getClaim("userId").asString()?.let { userId ->
                    if (userId.isNotEmpty()) JWTPrincipal(credential.payload) else null
                }
            }
        }
    }
}