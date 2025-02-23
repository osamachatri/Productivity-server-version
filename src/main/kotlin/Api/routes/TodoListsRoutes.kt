package com.oussama_chatri.Api.routes

import com.oussama_chatri.Api.requests.ToDoListRequest
import com.oussama_chatri.Api.requests.UpdatedToDoListRequest
import com.oussama_chatri.data.daos.ToDoListDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.toDoListsRoutes() {
    route("/todolists") {
        val toDoListDao by inject<ToDoListDao>()
        authenticate("auth-jwt") {
            post {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val request = call.receive<ToDoListRequest>()

                val toDoListId = toDoListDao.createToDoList(
                    ownerId = userId,
                    request
                )

                if (toDoListId > 0) call.respond(HttpStatusCode.OK, "It's successfully created to do list: $toDoListId")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully created to do list: $toDoListId")
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val toDoLists = toDoListDao.getAllToDoLists(userId)
                if (toDoLists.isNotEmpty()) call.respond(HttpStatusCode.OK, toDoLists)
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully not found")
            }

            put("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing user_id claim in token")

                val toDoListId = call.parameters["id"]?.toIntOrNull()
                if (toDoListId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@put
                }

                val request = call.receive<UpdatedToDoListRequest>()

                val updated = toDoListDao.updateToDoList(
                    toDoListId,
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

                val toDoListId = call.parameters["id"]?.toIntOrNull()

                if (toDoListId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@delete
                }

                val deleted = toDoListDao.deleteToDoList(toDoListId, userId)

                if (deleted) call.respond(HttpStatusCode.OK, "It's successfully deleted")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully deleted")
            }
        }
    }
}