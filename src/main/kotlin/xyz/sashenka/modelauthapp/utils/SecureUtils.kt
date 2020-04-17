package xyz.sashenka.modelauthapp.utils

import java.security.MessageDigest

object SecureUtils {
    @JvmStatic
    fun generateHash(plaintext: String, salt: String) =
        MessageDigest.getInstance("SHA-256")
            .digest((plaintext + salt).toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
}
