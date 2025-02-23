package com.oussama_chatri.Api.responses

import com.oussama_chatri.Api.requests.Work
import kotlinx.serialization.Serializable

@Serializable
data class ToDoListResponse(
    val title: String,
    val description: String?,
    val listOfWorks: List<Work>,
    val creationTime: Long,
    val editedTime: Long,
    val priority: String,
    val isPinned: Boolean,
    val progress: Float
)