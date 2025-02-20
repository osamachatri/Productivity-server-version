package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Events : Table<Nothing>("events") {
    val id = int("id").primaryKey()
    val ownerId = varchar("owner_id")
    val priority = varchar("priority")
    val type = varchar("type")
    val title = varchar("title")
    val description = text("description")
    val startTime = long("start_time")
    val endTime = long("end_time")
}

