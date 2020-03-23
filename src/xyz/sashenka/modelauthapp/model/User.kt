package xyz.sashenka.modelauthapp.model

data class User(
    val login: String,
    val hash: String,
    val salt: String
)