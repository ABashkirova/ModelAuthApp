package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import xyz.sashenka.modelauthapp.model.domain.User

data class DBUser(
    @Expose
    val id: Int,
    @Expose
    val login: String,
    val hash: String,
    val salt: String
) {
    fun toPlain(): User {
        return User(login, hash, salt)
    }
}
