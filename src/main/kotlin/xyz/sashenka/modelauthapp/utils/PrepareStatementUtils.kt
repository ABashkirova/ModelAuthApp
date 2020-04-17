package xyz.sashenka.modelauthapp.utils

import java.sql.PreparedStatement

fun PreparedStatement.setValues(vararg values: Any?) {
    for (i in values.indices) {
        this.setObject(i + 1, values[i])
    }
}
