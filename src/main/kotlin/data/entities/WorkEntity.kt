package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Works : Table<Nothing>("work") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val title = varchar("title")
    val description = text("description")
    val startTime = timestamp("start_time")
    val endTime = timestamp("end_time")
}
