package com.oussama_chatri.data.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object Users : Table<Nothing>("accounts") {
    val userId = int("user_id").primaryKey()
    val username = varchar("username")
    val fullName = varchar("full_name")
    val email = varchar("email")
    val phoneNumber = varchar("phone_number")
    val passwordHash = text("password_hash")
}
