package com.oussama_chatri.data.daos


import com.oussama_chatri.api.requests.NoteRequest
import com.oussama_chatri.api.requests.UpdatedNoteRequest
import com.oussama_chatri.api.responses.NoteResponse
import com.oussama_chatri.data.entities.Notes
import org.ktorm.database.Database
import org.ktorm.dsl.*

class NoteDao(private val database : Database ) {

    fun getAllNotesByUserId(userId: String): List<NoteResponse> {
        return database.from(Notes).select()
            .where(Notes.ownerId eq userId)
            .map { row ->
                NoteResponse(
                    row[Notes.title]!!,
                    row[Notes.content]!!,
                    row[Notes.type]!!,
                    row[Notes.creationTime]!!,
                    row[Notes.editedTime] ?: 154654564,
                    row[Notes.isPinned]!!
                )
            }
    }

    fun createNote(
        ownerId: String,
        request: NoteRequest
    ): Int {
        return database.insert(Notes) {
            set(it.ownerId, ownerId)
            set(it.title, request.title)
            set(it.content, request.content)
            set(it.type, request.type)
            set(it.creationTime, request.creationTime)
            set(it.editedTime, request.creationTime)
            set(it.isPinned, request.isPinned)
        }
    }

    fun updateNote(
        noteId: Int, ownerId: String,
        request: UpdatedNoteRequest
    ): Boolean {
        val affectedRows = database.update(Notes) {
            set(it.title, request.title)
            set(it.content, request.content)
            set(it.type, request.type)
            set(it.editedTime, request.editedTime)
            set(it.isPinned, request.isPinned)
            where { (Notes.id eq noteId) and (Notes.ownerId eq ownerId) }
        }
        return affectedRows > 0
    }

    fun deleteNote(noteId: Int, ownerId: String): Boolean {
        val affectedRows = database.delete(Notes) {
            (Notes.id eq noteId) and (Notes.ownerId eq ownerId)
        }
        return affectedRows > 0
    }
}

