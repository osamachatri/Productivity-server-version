package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Work(
    val id: Int? = null,
    val ownerId: Int,
    val title: String,
    val description: String,
    val startTime: String? = null,
    val endTime: String? = null
)
