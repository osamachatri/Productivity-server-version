package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int,
    val ownerId: Int,
    val priority: String,
    val type: String,
    val title: String,
    val description: String?,
    val startTime: Long,
    val endTime: Long?
)
