package controller

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

    private fun argsContainsFlag(flag: String): Boolean =
        args.contains(flag) && (args.size > args.indexOf(flag) + 1)
}