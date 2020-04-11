package xyz.sashenka.modelauthapp.model.domain

data class UsersResources(
    val path: String,
    val role: Role,
    val login: String
)