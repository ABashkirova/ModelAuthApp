package xyz.sashenka.modelauthapp.service

import org.apache.logging.log4j.kotlin.KotlinLogger
import org.flywaydb.core.Flyway
import java.sql.Connection
import java.sql.DriverManager

class DBService(private val logger: KotlinLogger) {
    private val envUrl: String = System.getenv("DBURL") ?: "jdbc:h2:file:"
    private val envDBFile: String? = System.getenv("DBFILE") ?: "./AAA"
    private val envLogin: String = System.getenv("DBLOGIN") ?: "sa"
    private val envPass: String = System.getenv("DBPASS") ?: ""
    private val migrationLocation = "db/migration"

    var connection: Connection? = null

    init {
        logger.info { ("Инициализируем DBService: url(${envUrl + envDBFile}), login($envLogin)") }
        migrate()
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
        logger.info { "Загрузка миграций flyway" }
        try {
            Flyway
                .configure()
                .dataSource(envUrl + envDBFile, envLogin, envPass)
                .locations(migrationLocation)
                .load()
                .migrate()
        } catch (ex: Exception) {
            logger.error { ex.stackTrace }
        }
    }

    fun connect() {
        if (connection == null) {
            logger.info { "Инициализируем подключение к БД" }
            try {
                connection = DriverManager.getConnection(envUrl + envDBFile, envLogin, envPass)
            } catch (ex: Exception) {
                logger.error { ex.stackTrace }
            }
        }
    }

    fun close() {
        logger.info { "Закрываем подключение к БД" }
        connection?.close()
        connection = null
    }
}
