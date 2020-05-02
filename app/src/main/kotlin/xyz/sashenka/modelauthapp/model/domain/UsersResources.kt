package xyz.sashenka.modelauthapp.model.domain

import com.google.gson.annotations.Expose

data class UsersResources(
    @Expose
    val path: String,
    @Expose
    val role: Role,
    @Expose
    val login: String
)
