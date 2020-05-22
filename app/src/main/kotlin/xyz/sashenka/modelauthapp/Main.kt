package xyz.sashenka.modelauthapp

import com.google.inject.Guice
import xyz.sashenka.modelauthapp.di.Container
import xyz.sashenka.modelauthapp.di.DatabaseModule
import kotlin.system.exitProcess

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val container = Container(Guice.createInjector(DatabaseModule()))
        container.getLogger(Main::class.java).info {
            "Инициализация: ${args.joinToString(" ")}"
        }

        val app = Application()
        val returnCode = app.run(args)

        container.getLogger(Main::class.java).info {
            "Завершение программы с кодом: ${returnCode.value}" + "\n---------"
        }

        exitProcess(returnCode.value)
    }
}
