package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int? = null,
    val ownerId: Int,
    val title: String,
    val description: String,
    val eventDate: String? = null,
    val location: String
)
