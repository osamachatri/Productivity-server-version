package com.oussama_chatri.Api.routes

import com.oussama_chatri.Api.requests.PomodoroRequest
import com.oussama_chatri.Api.requests.UpdatedPomodoroRequest
import com.oussama_chatri.data.daos.PomodoroDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.pomodoroRoutes() {
    route("/pomodoros") {
        val pomodoroDao by inject<PomodoroDao>()
        authenticate("auth-jwt") {
            post {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val request = call.receive<PomodoroRequest>()

                val pomodoroId = pomodoroDao.createPomodoro(
                    ownerId = userId,
                    request
                )

                if (pomodoroId > 0) call.respond(HttpStatusCode.OK, "It's successfully created pomodoro: $pomodoroId")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully created pomodoro: $pomodoroId")
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val pomodoros = pomodoroDao.getAllPomodoros(userId)
                if (pomodoros.isNotEmpty()) call.respond(HttpStatusCode.OK, pomodoros)
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully not found")
            }

            put("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val pomodoroId = call.parameters["id"]?.toIntOrNull()
                if (pomodoroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@put
                }

                val request = call.receive<UpdatedPomodoroRequest>()

                val updated = pomodoroDao.updatePomodoro(
                    pomodoroId,
                    userId,
                    request
                )

                if (updated) call.respond(HttpStatusCode.OK, "It's successfully updated")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully updated")
            }

            delete("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val pomodoroId = call.parameters["id"]?.toIntOrNull()

                if (pomodoroId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@delete
                }

                val deleted = pomodoroDao.deletePomodoro(pomodoroId, userId)

                if (deleted) call.respond(HttpStatusCode.OK, "It's successfully deleted")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully deleted")
            }
        }
    }
}