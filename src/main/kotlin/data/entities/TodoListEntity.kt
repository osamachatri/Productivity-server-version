package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object ToDoLists : Table<Nothing>("to_do_lists") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val title = varchar("title")
    val description = text("description")
    val listOfWorks = text("list_of_works")
    val creationTime = long("creation_time")
    val priority = varchar("priority")
    val isPinned = boolean("is_pinned")
    val progress = float("progress")
}

