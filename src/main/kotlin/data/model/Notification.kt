package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Int,
    val ownerId: String,
    val title : String,
    val content : String,
    val creationTime : Long,
    val priority : String
)
