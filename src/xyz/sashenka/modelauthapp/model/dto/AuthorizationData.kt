package xyz.sashenka.modelauthapp.model.dto

data class AuthorizationData(
    val path: String,
    val role: String,
    val login: String
)