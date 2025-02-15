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
data class NoteRequest(val title: String, val content: String, val userId : String, val editedAt: Long, val type: String)

fun Route.notesRoutes() {
    route("/notes") {
        authenticate("auth-jwt") {
            post {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("userId")?.asString() ?: throw IllegalArgumentException("Invalid userId in token")
                    println(userId)
                    val request = call.receive<NoteRequest>()

                    val noteId = NoteDao.createNote(userId = userId, title = request.title, content = request.content, editedAt = request.editedAt, type = request.type)
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
                val userId = principal!!.payload.getClaim("userId").asString() ?: throw IllegalArgumentException("Invalid userId in token")

                val notes = NoteDao.getUserNotes(userId)
                call.respond(HttpStatusCode.OK, notes)
            }

            put("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()
                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@put
                }

                val request = call.receive<NoteRequest>()
                val updated = NoteDao.updateNote(noteId, request.title, request.content, request.userId, request.editedAt, request.type)

                if (updated) {
                    call.respond(HttpStatusCode.OK, "Note updated successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }

            delete("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asString() ?: throw IllegalArgumentException("Invalid userId in token")
                val noteId = call.parameters["id"]?.toIntOrNull()

                if (noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note ID")
                    return@delete
                }

                val deleted = NoteDao.deleteNote(noteId, userId)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Note deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Note not found or access denied")
                }
            }
        }
    }
}