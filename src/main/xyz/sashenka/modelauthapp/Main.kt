package xyz.sashenka.modelauthapp

import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.service.DBService
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val dbService = DBService()
    val user = UserDAO(dbService).getUserByLogin("admin")

    val app = Application(args)
    val returnCode = app.run()
    exitProcess(returnCode.value)
}