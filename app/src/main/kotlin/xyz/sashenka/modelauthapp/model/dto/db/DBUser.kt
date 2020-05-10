package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import xyz.sashenka.modelauthapp.model.domain.User
import javax.persistence.*

@Entity
@Table(name = "USER")
data class DBUser(
    @Expose
    @Id
    @Column(name = "ID")
    val id: Int,

    @Expose
    @Column(name = "LOGIN")
    val login: String,

    @Column(name = "HASH_PASSWORD")
    val hash: String,

    @Column(name = "SALT")
    val salt: String
) {
    fun toPlain(): User {
        return User(login, hash, salt)
    }
}
