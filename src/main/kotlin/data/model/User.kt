package com.oussama_chatri.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val userId: Int,
    val username: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String?,
    val passwordHash: String
)
