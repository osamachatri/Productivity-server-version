package com.oussama_chatri.Api.comman

import kotlinx.serialization.Serializable

@Serializable
data class EventsRequestAndResponse(
    val priority: String,
    val type: String,
    val title: String,
    val description: String?,
    val startTime: Long,
    val endTime: Long?
)