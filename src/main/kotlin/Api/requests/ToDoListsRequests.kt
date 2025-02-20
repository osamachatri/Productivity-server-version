package com.oussama_chatri.Api.requests

import com.oussama_chatri.data.model.Work
import kotlinx.serialization.Serializable

@Serializable
data class ToDoListRequest(
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val creationTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)

@Serializable
data class UpdatedToDoListRequest(
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val editedTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)