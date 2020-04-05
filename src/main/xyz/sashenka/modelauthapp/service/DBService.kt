package xyz.sashenka.modelauthapp.service

import org.flywaydb.core.Flyway
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class DBService {
    private val envUrl: String = System.getenv("DBURL") ?: "jdbc:h2:file:"
    private val envDBFile: String? = System.getenv("DBFILE") ?: "./AAA"
    private val envLogin: String = System.getenv("DBLOGIN") ?: "sa"
    private val envPass: String = System.getenv("DBPASS") ?: ""

    var connection: Connection? = null

    init {
        println("init db")
        migrate()
        connect()
    }

    fun <R> inConnect(body: (db: Connection) -> R): R? {
        connect()
        if (connection != null) {
            val res = body(this.connection!!)
            close()
            return res
        }
        return null
    }

    private fun migrate() {
        val flyway = Flyway
            .configure()
            .dataSource(envUrl + envDBFile, envLogin, envPass)
            .locations("db/migration")
            .load()
        println("flyway load $flyway")
        val dbFileName = "$envDBFile.mv.db"
        if (!File(dbFileName).exists()) {
            println("flyway migrate")
            flyway.migrate()
        }
    }

    private fun connect() {
        if (connection == null) {
            println("init connect")
            connection = DriverManager.getConnection(envUrl + envDBFile, envLogin, envPass)
        }
    }

    private fun close() {
        println("connect close")
        connection?.close()
    }
}