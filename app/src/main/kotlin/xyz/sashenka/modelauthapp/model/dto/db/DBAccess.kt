package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose

data class DBAccess(
    @Expose
    val id: Int,
    @Expose
    val userId: Int,
    @Expose
    val resource: String,
    @Expose
    val role: String
)
