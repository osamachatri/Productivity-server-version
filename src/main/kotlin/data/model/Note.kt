package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int? = null,
    val ownerId: Int,
    val title: String,
    val content: String,
    val type: String,
    val creationTime: String? = null,
    val isPinned: Boolean = false
)
