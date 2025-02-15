package com.oussama_chatri.routes

import com.oussama_chatri.data.daos.UserDao
import com.oussama_chatri.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class RegisterRequest(val name : String, val username: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email : String, val password : String)


fun Route.authRoutes() {
    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()

            // Check if email is used before
            val existingEmail = UserDao.findUserByEmail(request.email)
            if (existingEmail != null) {
                call.respond(HttpStatusCode.NotAcceptable, "Email already in use")
                return@post
            }

            // Check if username is used before
            val existingUsername = UserDao.findUserByUsername(request.username)
            if (existingUsername != null) {
                call.respond(HttpStatusCode.NotAcceptable, "Username already in use")
                return@post
            }

            // Hash password
            val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())

            // Create user
            val userId = UserDao.createUser(request.name, request.username, request.email, password = hashedPassword!!)
            if (userId > 0) {
                call.respond(HttpStatusCode.Created, "User registered successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Error registering user")
            }
        }

        post("/login") {
            println("Login")

            try {
                val request = call.receive<LoginRequest>()
                println(request.email)
                println(request.password)
                println(request)

                val user = UserDao.findUserByEmail(request.email)
                println(user)

                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid email")
                    return@post
                }

                // Verify password
                val verify = BCrypt.checkpw(request.password, user.password)
                if (!verify) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid password")
                    return@post
                }

                // Generate JWT token
                val token = JwtConfig.generateToken(user.id.toString())
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            }catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}