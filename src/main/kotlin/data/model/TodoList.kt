package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ToDoList(
    val id: Int,
    val ownerId: String,
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val creationTime: Long,
    val editedTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)
