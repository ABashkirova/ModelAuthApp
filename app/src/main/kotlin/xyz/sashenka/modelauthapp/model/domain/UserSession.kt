package xyz.sashenka.modelauthapp.model.domain

import com.google.gson.annotations.Expose

data class UserSession(
    @Expose
    val user: String,
    @Expose
    val resource: String,
    @Expose
    val dateStart: String,
    @Expose
    val dateEnd: String,
    @Expose
    val volume: Int
)
