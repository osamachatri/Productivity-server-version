package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.NotesEntity
import com.oussama_chatri.data.model.Note
import org.ktorm.dsl.*

object NoteDao {
    fun createNote(
        title: String,
        content: String,
        userId: String,
        editedAt: Long,
        type: String,
    ): Int {
        return db.insert(NotesEntity) {
            set(it.title, title)
            set(it.content, content)
            set(it.userId, userId)
            set(it.editedAt, editedAt)
            set(it.type, type)
        }
    }

    fun getUserNotes(userId: String): List<Note> {
        return db.from(NotesEntity).select()
            .where { NotesEntity.userId eq userId }
            .map {
                Note(
                    id = it[NotesEntity.id]!!,
                    userId = it[NotesEntity.userId]!!,
                    title = it[NotesEntity.title]!!,
                    content = it[NotesEntity.content]!!,
                    editedAt = it[NotesEntity.editedAt]!!,
                    type = it[NotesEntity.type]!!,
                )
            }
    }

    fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        userId: String,
        editedAt: Long,
        type: String,
    ): Boolean {
        val updatedRows = db.update(NotesEntity) {
            set(it.title, title)
            set(it.content, content)
            set(it.editedAt, editedAt)
            set(it.type, type)
            where {
                (it.id eq noteId) and (it.userId eq userId)
            }
        }
        return updatedRows > 0
    }

    fun deleteNote(noteId: Int, userId: String): Boolean {
        val deletedRows = db.delete(NotesEntity) {
            (it.id eq noteId) and (it.userId eq userId)
        }
        return deletedRows > 0
    }
}