package com.oussama_chatri.routes

import com.oussama_chatri.data.daos.NoteDao
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val title: String,
    val content: String,
    val type: String,
    val creationTime: Long,
    val isPinned: Boolean
)

@Serializable
data class UpdatedNoteRequest(
    val title: String,
    val content: String,
    val type: String,
    val editedTime: Long,
    val isPinned: Boolean
)

fun Route.notesRoutes() {
    route("/notes") {
        authenticate("auth-jwt") {
            post {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("user_id")?.asString()
                        ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")
                    println(userId)

                    val request = call.receive<NoteRequest>()

                    val noteId = NoteDao().createNote(
                        ownerId = userId,
                        title = request.title,
                        content = request.content,
                        type = request.type,
                        creationTime = request.creationTime,
                        editedTime = request.creationTime,
                        isPinned = request.isPinned
                    )
                    if (noteId > 0) {
                        call.respond(HttpStatusCode.Created, "It's successfully created note: $noteId" )
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "It's not successfully created note: $noteId")
                    }
                }catch (ex: Exception){
                    ex.printStackTrace()
                }

            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()

                val notes = NoteDao().getAllNotesByUserId(userId ?: "-1")
                call.respond(HttpStatusCode.OK, notes)
            }

            put("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()

                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@put
                }

                val request = call.receive<UpdatedNoteRequest>()

                val updated = NoteDao().updateNote(
                    noteId = noteId,
                    ownerId = userId ?: "-1",
                    title = request.title,
                    content = request.content,
                    type = request.type,
                    editedTime = request.editedTime,
                    isPinned = request.isPinned
                )

                if (updated) {
                    call.respond(HttpStatusCode.OK, "Note updated successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }

            delete("/{id}") {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("user_id")?.asString()
                    ?: throw IllegalArgumentException("Missing or invalid user_id claim in token")

                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@delete
                }

                val deleted = NoteDao().deleteNote(noteId, userId)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Note deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }
        }
    }
}