package xyz.sashenka.modelauthapp.model.dto.args

data class AccountingData(
    val login: String,
    val resource: String,
    val startDate: String,
    val endDate: String,
    val volume: String
)
