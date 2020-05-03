package xyz.sashenka.webapplication

fun String.isInteger(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
