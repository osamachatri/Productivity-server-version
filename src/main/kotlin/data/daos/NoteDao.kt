package com.oussama_chatri.data.daos


import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.entities.Notes
import com.oussama_chatri.data.model.Note
import org.ktorm.database.Database
import org.ktorm.dsl.*

class NoteDao(private val database: Database = DatabaseFactory.db) {
    fun getAllNotesByUserId(userId : String): List<Note> {
        return database.from(Notes).select()
            .where(Notes.ownerId eq userId)
            .map { row -> Note(
                row[Notes.id]!!,
                row[Notes.ownerId]!!,
                row[Notes.title]!!,
                row[Notes.content]!!,
                row[Notes.type]!!,
                row[Notes.creationTime]!!,
                row[Notes.editedTime] ?: 154654564,
                row[Notes.isPinned]!!
            ) }
    }

    fun createNote(
        ownerId: String,
        title: String,
        content: String,
        type: String,
        creationTime: Long,
        editedTime: Long,
        isPinned: Boolean
    ): Int {
        return database.insert(Notes) {
            set(it.ownerId, ownerId)
            set(it.title, title)
            set(it.content, content)
            set(it.type, type)
            set(it.creationTime, creationTime)
            set(it.editedTime, editedTime)
            set(it.isPinned, isPinned)
        }
    }

    fun updateNote(noteId : Int, ownerId: String,
                   title: String, content: String, type: String, editedTime: Long, isPinned: Boolean): Boolean {
        val affectedRows = database.update(Notes) {
            set(it.title, title)
            set(it.content, content)
            set(it.type, type)
            set(it.editedTime, editedTime)
            set(it.isPinned, isPinned)
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

