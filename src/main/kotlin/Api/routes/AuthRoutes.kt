package com.oussama_chatri.Api.routes

import com.oussama_chatri.data.daos.AccountDao
import com.oussama_chatri.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class RegisterRequest(
    val username: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String?,
    val password: String
)

@Serializable
data class LoginRequestByEmail(val email : String, val password : String)

@Serializable
data class LoginRequestByUsername(val username : String, val password : String)


fun Route.authRoutes() {
    route("/auth") {
        post("/register") {

            val request = call.receive<RegisterRequest>()

            // Check if email is used before
            val existingEmail = AccountDao().getAccountByEmail(request.email)
            if (existingEmail != null) {
                call.respond(HttpStatusCode.NotAcceptable, "Email already in use")
                return@post
            }

            // Check if username is used before
            val existingUsername = AccountDao().getAccountByUsername(request.username)
            if (existingUsername != null) {
                call.respond(HttpStatusCode.NotAcceptable, "Username already in use")
                return@post
            }

            // Hash password
            val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())

            // Create user
            val userId = AccountDao().createAccount(
                username = request.username,
                fullName = request.fullName,
                email = request.email,
                phoneNumber = request.phoneNumber,
                passwordHash = hashedPassword
            )

            println(userId)

            if (userId > 0) {
                // Generate JWT token
                val token = JwtConfig.generateToken(userId.toString())
                call.respond(HttpStatusCode.OK, "User registered successfully" + mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Error registering user")
            }


        }

        post("/login") {

            val type = call.request.queryParameters["type"]

            val request = when(type){
                "email" -> call.receive<LoginRequestByEmail>()
                else -> call.receive<LoginRequestByUsername>()
            }

            when (request) {
                is LoginRequestByEmail -> {
                    val user = AccountDao().getAccountByEmail(request.email)
                    if (user != null){
                        val verifyPassword = BCrypt.checkpw(request.password, user.passwordHash)
                        if (verifyPassword) {
                            val token = JwtConfig.generateToken(user.userId.toString())
                            call.respond(HttpStatusCode.OK, mapOf("token" to token))
                        }else {
                            call.respond(HttpStatusCode.Unauthorized, "Passwords do not match")
                            return@post
                        }

                    }else{
                        call.respond(HttpStatusCode.NotFound, "The email does not exist")
                        return@post
                    }
                }
                is LoginRequestByUsername -> {
                    val user = AccountDao().getAccountByUsername(request.username)
                    if (user != null) {
                        val verifyPassword = BCrypt.checkpw(request.password, user.passwordHash)
                        if (verifyPassword) {
                            val token = JwtConfig.generateToken(user.userId.toString())
                            call.respond(HttpStatusCode.OK, mapOf("token" to token))
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "Passwords do not match")
                            return@post
                        }
                    }else{
                        call.respond(HttpStatusCode.NotFound, "The username does not exist")
                        return@post
                    }
                }
            }

        }
    }
}