package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import java.util.Date

class DBUserSession {
    @Expose
    var id: Int = 1
    @Expose
    var dateStart: Date = Date()
    @Expose
    var dateEnd: Date = Date()
    @Expose
    var volume: Int = 0
    var access: DBAccess? = null
    var version: Long = 1
}
