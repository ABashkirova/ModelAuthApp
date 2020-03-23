package xyz.sashenka.modelauthapp.model

enum class ExitCode(val value: Int) {
    SUCCESS_CODE(0),
    HELP_CODE(1),
    INVALID_LOGIN_FORMAT(2),
    UNKNOWN_LOGIN(3),
    INVALID_PASSWORD(4)
}