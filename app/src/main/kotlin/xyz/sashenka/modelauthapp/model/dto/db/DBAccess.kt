package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose

class DBAccess() {
    @Expose var id: Int = 1
    @Expose var resource: String = ""
    @Expose var role: String = ""
    var user: DBUser? = null
    var version: Long = 1

    constructor(id: Int, user: DBUser, resource: String, role: String) : this() {
        this.id = id
        this.user = user
        this.resource = resource
        this.role = role
    }
}
