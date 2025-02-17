package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object TodoLists : Table<Nothing>("todo_lists") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val title = varchar("title")
    val description = text("description")
    val creationTime = timestamp("creation_time")
    val isCompleted = boolean("is_completed")
}
