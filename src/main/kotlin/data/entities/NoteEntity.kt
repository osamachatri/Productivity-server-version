package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object NotesEntity : Table<Nothing>("notes") {
    val id = int("id").primaryKey()
    val title = varchar("title")
    val content = text("content")
    val userId = varchar("user_id")
    val editedAt = long("edited_at")
    val type = varchar("type")
}