package xyz.sashenka.modelauthapp

import com.google.inject.Guice
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.di.ApplicationModule
import xyz.sashenka.modelauthapp.di.Container
import xyz.sashenka.modelauthapp.di.DatabaseModule
import kotlin.system.exitProcess

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val injector = Guice.createInjector(DatabaseModule(), ApplicationModule())
        val logger = loggerOf(Main::class.java)
        logger.info {
            "Инициализация: ${args.joinToString(" ")}"
        }

        val app = injector.getInstance(Application::class.java)
        val returnCode = app.run(args)

        logger.info {
            "Завершение программы с кодом: ${returnCode.value}" + "\n---------"
        }

        exitProcess(returnCode.value)
    }
}
