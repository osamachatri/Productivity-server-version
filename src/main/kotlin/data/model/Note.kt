package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val userId: String,
    val editedAt : Long,
    val type : String,
)