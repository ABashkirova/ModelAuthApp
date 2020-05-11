package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GeneratedValue
import xyz.sashenka.modelauthapp.model.domain.User

@Entity
@Table(name = "USER")
data class DBUser @JvmOverloads constructor(
    @Expose @Id @GeneratedValue @Column(name = "ID")
    val id: Int = 0,

    @Expose @Column(name = "LOGIN")
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
