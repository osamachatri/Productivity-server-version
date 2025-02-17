package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Work(
    val ownerId: Int,
    val title: String,
    val description: String?,
    val startTime: Long,
    val endTime: Long?,
    val progress: Float
)
