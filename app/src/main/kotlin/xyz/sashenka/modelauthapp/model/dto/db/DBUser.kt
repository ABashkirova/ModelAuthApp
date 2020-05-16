package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import xyz.sashenka.modelauthapp.model.domain.User
import javax.persistence.*

@Entity
@Table(name = "users")
data class DBUser @JvmOverloads constructor(
    @Expose @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    val id: Int = 0,

    @Expose @Column(name = "login")
    val login: String,

    @Column(name = "hash_password")
    val hash: String,

    @Column(name = "salt")
    val salt: String,

    @Version
    val version: Long = 1
) {
    fun toPlain(): User {
        return User(login, hash, salt)
    }
}
