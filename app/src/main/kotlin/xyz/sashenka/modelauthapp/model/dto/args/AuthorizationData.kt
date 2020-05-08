package xyz.sashenka.modelauthapp.model.dto.args

data class AuthorizationData(
    val path: String,
    val role: String,
    val login: String
)
