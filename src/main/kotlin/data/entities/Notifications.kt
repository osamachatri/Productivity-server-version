package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Notifications : Table<Nothing>("notifications") {
    val id = int("id").primaryKey()
    val ownerId = varchar("owner_id")
    val title = varchar("title")
    val content = text("content")
    val creationTime = long("creation_time")
    val priority = varchar("priority")
}