package com.oussama_chatri.routes

import com.oussama_chatri.data.daos.ToDoListDao
import com.oussama_chatri.data.model.Work
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ToDoListRequest(
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val creationTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)

@Serializable
data class UpdatedToDoListRequest(
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val editedTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)

fun Route.toDoListsRoutes(){
    route("/dolists"){
        authenticate("auth-jwt") {
            post {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val request = call.receive<ToDoListRequest>()

                val toDoListId = ToDoListDao().createToDoList(
                        ownerId = userId,
                        title = request.title,
                        description = request.description,
                        listOfWorks = request.listOfWorks,
                        creationTime = request.creationTime,
                        priority = request.priority,
                        isPinned = request.isPinned,
                        progress = request.progress,
                )

                if (toDoListId >0) call.respond(HttpStatusCode.OK, "It's successfully created note: $toDoListId")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully created note: $toDoListId")
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString() ?: throw IllegalArgumentException("Missing user_id claim in token")

                val toDoLists = ToDoListDao().getAllToDoLists(userId)
                if (toDoLists.isNotEmpty()) call.respond(HttpStatusCode.OK, toDoLists)
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully not found")
            }

            put ("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                ?: throw IllegalArgumentException("Missing user_id claim in token")

                val toDoListId = call.parameters["id"]?.toIntOrNull()
                if (toDoListId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@put
                }

                val request = call.receive<UpdatedToDoListRequest>()

                val updated = ToDoListDao().updateToDoList(
                    toDoListId,
                    userId,
                    request.title,
                    request.description,
                    request.listOfWorks,
                    request.editedTime,
                    request.priority,
                    request.isPinned,
                    request.progress
                )

                if (updated) call.respond(HttpStatusCode.OK, "It's successfully updated")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully updated")
            }

            delete ("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                ?: throw IllegalArgumentException("Missing user_id claim in token")

                val toDoListId = call.parameters["id"]?.toIntOrNull()

                if (toDoListId == null){
                    call.respond(HttpStatusCode.BadRequest, "Bad request")
                    return@delete
                }

                val deleted = ToDoListDao().deleteToDoList(toDoListId, userId)

                if (deleted) call.respond(HttpStatusCode.OK, "It's successfully deleted")
                else call.respond(HttpStatusCode.InternalServerError, "It's not successfully deleted")
            }
        }
    }
}