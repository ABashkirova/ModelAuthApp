package xyz.sashenka.modelauthapp.model

import com.google.gson.annotations.Expose

data class ResultCode(
    @Expose val exitCode: ExitCode,
    @Expose val message: String,
    @Expose val userId: Int? = null,
    @Expose val accessId: Int? = null
)
