package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int? = null,
    val username: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String?,
    val passwordHash: String
)

