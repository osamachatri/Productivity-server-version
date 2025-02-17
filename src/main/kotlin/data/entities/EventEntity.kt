package com.oussama_chatri.data.entities
import org.ktorm.schema.*

object Events : Table<Nothing>("events") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val title = varchar("title")
    val description = text("description")
    val eventDate = timestamp("event_date")
    val location = varchar("location")
}
