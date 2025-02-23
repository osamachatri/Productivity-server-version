package com.oussama_chatri.Api.routes

import com.oussama_chatri.Api.comman.EventsRequestAndResponse
import com.oussama_chatri.data.daos.EventDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.eventRoutes() {
    route("/events") {
        val eventDao by inject<EventDao>()
        authenticate("auth-jwt") {
            post {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val request = call.receive<EventsRequestAndResponse>()

                val eventId = eventDao.createEvent(
                    ownerId = userId,
                    request
                )

                if (eventId > 0) call.respond(HttpStatusCode.OK, "It's successfully created event: $eventId")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully created event: $eventId")
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val events = eventDao.getAllEvents(userId)
                if (events.isNotEmpty()) call.respond(HttpStatusCode.OK, events)
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully not found")
            }

            put("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val eventID = call.parameters["id"]?.toIntOrNull()
                if (eventID == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@put
                }

                val request = call.receive<EventsRequestAndResponse>()

                val updated = eventDao.updateEvent(
                    eventID,
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

                val eventId = call.parameters["id"]?.toIntOrNull()

                if (eventId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@delete
                }

                val deleted = eventDao.deleteEvent(eventId, userId)

                if (deleted) call.respond(HttpStatusCode.OK, "It's successfully deleted")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully deleted")
            }
        }
    }
}