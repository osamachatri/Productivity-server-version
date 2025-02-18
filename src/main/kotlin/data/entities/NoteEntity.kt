package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Notes : Table<Nothing>("notes") {
    val id = int("id").primaryKey()
    val ownerId = varchar("owner_id")
    val title = varchar("title")
    val content = text("content")
    val type = varchar("type")
    val creationTime = long("creation_time")
    val editedTime = long("edited_time")
    val isPinned = boolean("is_pinned")
}
