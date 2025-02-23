package com.oussama_chatri.Api.requests

import kotlinx.serialization.Serializable

@Serializable
data class PomodoroRequest(
    val priority: String,
    val title: String,
    val description: String?,
    val numPomodoros: Int,
    val creationTime: Long,
    val pomodoroTime: Int,
    val shortBreakTime: Int,
    val longBreakTime: Int,
    val progress: Float
)


@Serializable
data class UpdatedPomodoroRequest(
    val priority: String,
    val title: String,
    val description: String?,
    val numPomodoros: Int,
    val editedTime: Long,
    val pomodoroTime: Int,
    val shortBreakTime: Int,
    val longBreakTime: Int,
    val progress: Float
)