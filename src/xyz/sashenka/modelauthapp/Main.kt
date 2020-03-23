package xyz.sashenka.modelauthapp

import xyz.sashenka.modelauthapp.Application
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = Application(args)
    val returnCode = app.run()
    exitProcess(returnCode.value)
}