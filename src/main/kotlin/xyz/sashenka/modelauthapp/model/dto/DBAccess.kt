package xyz.sashenka.modelauthapp.model.dto

data class DBAccess(
    val id: Int,
    val userId: Int,
    val resource: String,
    val role: String
)