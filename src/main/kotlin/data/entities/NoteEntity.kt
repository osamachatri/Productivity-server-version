package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Notes : Table<Nothing>("notes") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val title = varchar("title")
    val content = text("content")
    val type = varchar("type")
    val creationTime = timestamp("creation_time")
    val isPinned = boolean("is_pinned")
}
