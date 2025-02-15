package com.oussama_chatri.data.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity : Table<Nothing>("users") {
    val id = int("id").primaryKey()
    val name = varchar("name")
    val username = varchar("username")
    val email = varchar("email")
    val password = varchar("password")
    val level = int("level")
}