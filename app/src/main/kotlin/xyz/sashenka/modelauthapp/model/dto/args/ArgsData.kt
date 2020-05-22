package xyz.sashenka.modelauthapp.model.dto.args

import com.google.gson.annotations.Expose

data class ArgsData(
    @Expose val login: String,
    @Expose val password: String,
    @Expose val resource: String,
    @Expose val role: String,
    @Expose val dateStart: String,
    @Expose val dateEnd: String,
    @Expose val volume: String
) {
    fun toArgsArray(): Array<String> {
        return arrayOf(
            "-login", login,
            "-pass", password,
            "-res", resource,
            "-role", role,
            "-ds", dateStart,
            "-de", dateEnd,
            "-vol", volume
        )
    }
}
