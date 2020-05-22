package xyz.sashenka.modelauthapp.model

import com.google.gson.annotations.SerializedName

enum class ExitCode(val value: Int) {
    @SerializedName("SUCCESS") SUCCESS(0),
    @SerializedName("HELP") HELP(1),
    @SerializedName("INVALID_LOGIN_FORMAT") INVALID_LOGIN_FORMAT(2),
    @SerializedName("UNKNOWN_LOGIN") UNKNOWN_LOGIN(3),
    @SerializedName("WRONG_PASSWORD") WRONG_PASSWORD(4),
    @SerializedName("UNKNOWN_ROLE") UNKNOWN_ROLE(5),
    @SerializedName("NO_ACCESS") NO_ACCESS(6),
    @SerializedName("INVALID_ACTIVITY") INVALID_ACTIVITY(7),
    @SerializedName("DI_ERROR") DI_ERROR(8),
    @SerializedName("DB_ERROR") DB_ERROR(9),
    @SerializedName("APP_ERROR") APP_ERROR(-1)
}
