package xyz.sashenka.modelauthapp.service

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import org.flywaydb.core.Flyway
import java.sql.Connection
import java.util.*

class DBService {
    private val logger: KotlinLogger = logger()
    private val envDriver: String = System.getenv("DBDRIVER") ?: "org.h2.Driver"
    private val envUrl: String = System.getenv("DBURL") ?: "jdbc:h2:file:./AAA"
    private val envLogin: String = System.getenv("DBLOGIN") ?: "sa"
    private val envPass: String = System.getenv("DBPASS") ?: ""
    private val migrationLocation = "db/migration"
    private lateinit var cpds: ComboPooledDataSource

    init {
        logger.info {
            "Переменные окружения: " +
                "[${System.getenv("DBDRIVER")}, ${System.getenv("DBURL")}]"
        }
        logger.info { "Инициализируем DBService: url($envUrl), login($envLogin)" }
    }

    fun migrate() {
        logger.info { "Загрузка миграций flyway" }
        try {
            Flyway
                .configure()
                .dataSource(envUrl, envLogin, envPass)
                .locations(migrationLocation)
                .load()
                .migrate()
        } catch (ex: Exception) {
            logger.error { ex.stackTrace }
        }
    }

    private fun initPoolDataSource() {
        cpds = ComboPooledDataSource()
        cpds.driverClass = envDriver
        cpds.jdbcUrl = envUrl
        cpds.user = envLogin
        cpds.password = envPass

        val properties = Properties()
        properties.setProperty("user", envLogin)
        properties.setProperty("password", envPass)
        properties.setProperty("useUnicode", "true")
        properties.setProperty("characterEncoding", "UTF8")
        cpds.properties = properties

        cpds.maxStatements = 180
        cpds.maxStatementsPerConnection = 180
        cpds.minPoolSize = 50
        cpds.acquireIncrement = 10
        cpds.maxPoolSize = 60
        cpds.maxIdleTime = 30
    }

    fun provideConnect(): Connection {
        val connection = cpds.connection
        logger.info(
            "Запрошен коннект у пула: ${connection.metaData}, " +
                "idleConnections =${cpds.numIdleConnections}, " +
                "busyConnections = ${cpds.numBusyConnections}"
        )
        return connection
    }
}
