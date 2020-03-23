package xyz.sashenka.modelauthapp.controller

enum class ArgKey(val value: String) {
    HELP("-h"),
    LOGIN("-login"),
    PASSWORD("-pass")
}

class ArgHandler(private val args: Array<String>) {

    fun getValue(key: ArgKey): String? {
        val flag = key.value
        return if (
            key != ArgKey.HELP
            && argsContainsFlag(flag)
        ) {
            args[args.indexOf(flag) + 1]
        } else null
    }

    fun authenticationIsNeeded(): Boolean = when {
        argsAreNotEmpty() && args.size >= 4 -> {
            argsContainsFlag(ArgKey.LOGIN.value)
                    && argsContainsFlag(ArgKey.PASSWORD.value)
        }
        else -> false
    }

    fun helpIsNeeded(): Boolean {
        return if (argsAreNotEmpty()) {
            args.contains(ArgKey.HELP.value)
        } else true
    }

    private fun argsAreNotEmpty(): Boolean = args.isNotEmpty()

    private fun argsContainsFlag(flag: String): Boolean =
        args.contains(flag) && (args.size > args.indexOf(flag) + 1)
}