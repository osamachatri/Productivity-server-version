package com.oussama_chatri.Api.responses

import kotlinx.serialization.Serializable

@Serializable
data class PomodoroResponse(
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