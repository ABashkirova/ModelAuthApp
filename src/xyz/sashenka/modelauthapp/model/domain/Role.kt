package xyz.sashenka.modelauthapp.model.domain

enum class Role {
    READ, WRITE, EXECUTE;

    companion object {
        fun getNames() = values().map { it.name }
    }
}