package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val content: String,
    val type: String,
    val creationTime: Long,
    val isPinned: Boolean
)
