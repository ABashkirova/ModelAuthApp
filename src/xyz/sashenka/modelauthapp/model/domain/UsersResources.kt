package xyz.sashenka.modelauthapp.model.domain

import xyz.sashenka.modelauthapp.model.domain.Role

data class UsersResources(
    val path: String,
    val role: Role,
    val login: String
)