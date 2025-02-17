package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoList(
    val id: Int? = null,
    val ownerId: Int,
    val title: String,
    val description: String,
    val creationTime: String? = null,
    val isCompleted: Boolean = false
)
