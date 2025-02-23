package com.oussama_chatri.data.entities

import org.ktorm.schema.*

object Pomodoros : Table<Nothing>("pomodoro") {
    val id = int("id").primaryKey()
    val ownerId = varchar("owner_id")
    val priority = varchar("priority")
    val title = varchar("title")
    val description = text("description")
    val numPomodoros = int("num_pomodoros")
    val creationTime = long("creation_time")
    val editedTime = long("edited_time")
    val pomodoroTime = int("pomodoro_time")
    val shortBreakTime = int("short_break_time")
    val longBreakTime = int("long_break_time")
    val progress = float("progress")
}

