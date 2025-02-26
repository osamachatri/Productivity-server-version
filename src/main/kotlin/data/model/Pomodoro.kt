package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Pomodoro(
    val id: Int,
    val ownerId: String,
    val priority: String,
    val title: String,
    val description: String?,
    val numPomodoros: Int,
    val creationTime: Long,
    val editedTime : Long,
    val pomodoroTime: Int,
    val shortBreakTime: Int,
    val longBreakTime: Int,
    val progress: Float
)
