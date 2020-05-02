package xyz.sashenka.modelauthapp.model.domain

import com.google.gson.annotations.Expose

data class User(
    @Expose
    val id: Int,
    @Expose
    val login: String,
    val hash: String,
    val salt: String
)
