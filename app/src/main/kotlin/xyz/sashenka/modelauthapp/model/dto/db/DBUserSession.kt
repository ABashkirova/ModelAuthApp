package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import java.util.*

data class DBUserSession(
    @Expose
    val id: Int,
    @Expose
    val accessId: Int,
    @Expose
    val dateStart: Date,
    @Expose
    val dateEnd: Date,
    @Expose
    val volume: Int
)
