package xyz.sashenka.modelauthapp

import xyz.sashenka.modelauthapp.di.Container
import kotlin.system.exitProcess

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val container = Container()
        container.getLogger().info { "Инициализация: ${args.joinToString(" ")}" }

        val app = Application(args, container)
        val returnCode = app.run()

        container.getLogger().info { "Завершение программы с кодом: ${returnCode.value}" + "\n---------" }
        exitProcess(returnCode.value)
    }
}