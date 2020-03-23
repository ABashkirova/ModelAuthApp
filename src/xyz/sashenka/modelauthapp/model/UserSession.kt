package xyz.sashenka.modelauthapp.model

import java.util.*

data class UserSession(
    val user: String,
    val resource: String,
    val dateStart: Date,
    val dateEnd: Date,
    val volume: Int
)