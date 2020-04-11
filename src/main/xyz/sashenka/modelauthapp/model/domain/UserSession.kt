package xyz.sashenka.modelauthapp.model.domain

data class UserSession(
    val user: String,
    val resource: String,
    val dateStart: String,
    val dateEnd: String,
    val volume: Int
)