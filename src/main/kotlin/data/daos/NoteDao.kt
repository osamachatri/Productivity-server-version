package com.oussama_chatri.data.daos


import com.oussama_chatri.data.entities.Notes
import com.oussama_chatri.data.model.Note
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class NoteDao(private val database: Database) {
    fun getAllNotes(): List<Note> {
        return database.from(Notes).select()
            .map { row -> Note(
                row[Notes.id]!!,
                row[Notes.ownerId]!!,
                row[Notes.title]!!,
                row[Notes.content]!!,
                row[Notes.type]!!,
                row[Notes.creationTime]!!,
                row[Notes.isPinned]!!
            ) }
    }

    fun createNote(note: Note) {
        database.insert(Notes) {
            set(it.ownerId, note.ownerId)
            set(it.title, note.title)
            set(it.content, note.content)
            set(it.type, note.type)
            set(it.creationTime, note.creationTime)
            set(it.isPinned, note.isPinned)
        }
    }
}

