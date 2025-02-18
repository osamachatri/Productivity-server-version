package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val ownerId: String,
    val title: String,
    val content: String,
    val type: String,
    val creationTime: Long,
    val editedTime : Long,
    val isPinned: Boolean
)
