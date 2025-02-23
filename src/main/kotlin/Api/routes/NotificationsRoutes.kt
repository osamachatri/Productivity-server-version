package com.oussama_chatri.Api.routes

import com.oussama_chatri.Api.comman.NotificationRequestAndResponse
import com.oussama_chatri.data.daos.NotificationDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.notificationsRoutes() {
    route("/notifications") {
        val notificationsDao by inject<NotificationDao>()
        authenticate("auth-jwt") {
            post {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("user_id")?.asString()
                        ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")
                    println(userId)

                    val request = call.receive<NotificationRequestAndResponse>()

                    val notificationId = notificationsDao.createNotification(
                        ownerId = userId,
                        request
                    )
                    if (notificationId > 0) {
                        call.respond(HttpStatusCode.Created, "It's successfully created Notification: $notificationId")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "It's not successfully created Notification: $notificationId")
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()

                val notes = notificationsDao.getAllNotifications(userId ?: "-1")
                call.respond(HttpStatusCode.OK, notes)
            }

            put("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()

                val notificationId = call.parameters["id"]?.toIntOrNull()

                if (notificationId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Notification ID")
                    return@put
                }

                val request = call.receive<NotificationRequestAndResponse>()

                val updated = notificationsDao.updateNotification(
                    notificationId = notificationId,
                    ownerId = userId ?: "-1",
                    request
                )

                if (updated) {
                    call.respond(HttpStatusCode.OK, "Notification updated successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Notification not found or access denied")
                }
            }

            delete("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val notificationId = call.parameters["id"]?.toIntOrNull()

                if (notificationId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@delete
                }

                val deleted = notificationsDao.deleteNotification(notificationId, userId)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Notification deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Notification not found or access denied")
                }
            }
        }
    }
}