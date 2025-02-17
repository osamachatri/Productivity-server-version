package com.oussama_chatri.data.daos


import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.Notes
import com.oussama_chatri.data.model.Note
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class NoteDao {
    fun getAllNotes(): List<Note> {
        return db.from(Notes).select().map { row ->
            Note(
                id = row[Notes.id],
                ownerId = row[Notes.ownerId]!!,
                title = row[Notes.title]!!,
                content = row[Notes.content]!!,
                type = row[Notes.type]!!,
                creationTime = row[Notes.creationTime].toString(),
                isPinned = row[Notes.isPinned] ?: false
            )
        }
    }

    fun addNote(note: Note): Boolean {
        val inserted = db.insert(Notes) {
            set(Notes.ownerId, note.ownerId)
            set(Notes.title, note.title)
            set(Notes.content, note.content)
            set(Notes.type, note.type)
            set(Notes.isPinned, note.isPinned)
        }
        return inserted > 0
    }
}
