package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.persist.jpa.JpaPersistModule
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import org.flywaydb.core.Flyway
import java.util.*

class DatabaseModule : AbstractModule() {
    private val logger: KotlinLogger = logger()
    private val persistenceUnitName = "AaaPersistenceUnit"
    private var envDriver: String = ""
    private var envUrl: String = ""
    private var envLogin: String = ""
    private var envPass: String = ""
    private var hibernateDialect = ""
    private val migrationLocation = "db/migration"

    override fun configure() {
        super.configure()
        setEnv()
        val jpaModule = JpaPersistModule(persistenceUnitName)
        jpaModule.properties(getDatabaseEnvironments())
        install(jpaModule)
        bind(PersistenceInitializer::class.java).asEagerSingleton()
        migrate()
    }

    private fun getDatabaseEnvironments(): MutableMap<String, Any> {
        logger.info {
            "Env to map"
        }
        val envConfig: MutableMap<String, Any> = HashMap()
        envConfig["javax.persistence.jdbc.url"] = envUrl
        envConfig["javax.persistence.jdbc.user"] = envLogin
        envConfig["javax.persistence.jdbc.password"] = envPass
        envConfig["javax.persistence.jdbc.driver"] = envDriver
        envConfig["hibernate.dialect"] = hibernateDialect
        return envConfig
    }

    private fun setEnv() {
        val url = System.getenv("DATABASE_URL")
        logger.info { "Env: [DATABASE_URL, $url]" }
        if (url.isNullOrEmpty()) {
            logger.info { "url.isNullOrEmpty(), use default local" }
            // TODO: переписать на Property
            envUrl = "jdbc:h2:file:./AAA"
            envLogin = "sa"
            envPass = ""
            envDriver = "org.h2.Driver"
            hibernateDialect = "org.hibernate.dialect.H2Dialect"
        } else {
            logger.info { "setEnv from url" }
            envUrl = System.getenv("JDBC_DATABASE_URL")
            envLogin = System.getenv("JDBC_DATABASE_USERNAME")
            envPass = System.getenv("JDBC_DATABASE_PASSWORD")
            envDriver = "org.postgresql.Driver"
            hibernateDialect = "org.hibernate.dialect.PostgreSQLDialect"
        }
    }

    private fun migrate() {
        logger.info { "Flyway migration" }
        Flyway
            .configure()
            .dataSource(envUrl, envLogin, envPass)
            .locations(migrationLocation)
            .load()
            .migrate()
    }
}
