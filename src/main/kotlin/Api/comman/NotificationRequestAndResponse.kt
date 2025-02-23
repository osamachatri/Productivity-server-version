package com.oussama_chatri.Api.comman

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequestAndResponse(
    val title : String,
    val content : String,
    val creationTime : Long,
    val priority : String
)