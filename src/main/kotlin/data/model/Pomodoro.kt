package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Pomodoro(
    val id: Int? = null,
    val ownerId: Int,
    val sessionCount: Int,
    val focusTime: Int,
    val breakTime: Int
)
