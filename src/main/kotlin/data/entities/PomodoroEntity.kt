package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Pomodoro : Table<Nothing>("pomodoro") {
    val id = int("id").primaryKey()
    val ownerId = int("owner_id")
    val sessionCount = int("session_count")
    val focusTime = int("focus_time")
    val breakTime = int("break_time")
}
