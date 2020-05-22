package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import xyz.sashenka.modelauthapp.model.domain.User

class DBUser() {
    @Expose
    var id: Int = 1

    @Expose
    var login: String = ""
    var salt: String = ""
    var hash: String = ""
    var version: Long = 1

    constructor(id: Int, login: String, salt: String, hash: String) : this() {
        this.id = id
        this.login = login
        this.salt = salt
        this.hash = hash
    }

    fun toPlain(): User {
        return User(login, hash, salt)
    }
}
