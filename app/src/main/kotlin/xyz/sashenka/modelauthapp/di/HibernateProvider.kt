package xyz.sashenka.modelauthapp.di

import com.google.inject.Provider
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import org.flywaydb.core.Flyway
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

@Singleton
class HibernateProvider : Provider<SessionFactory> {
    private var sessionFactory: SessionFactory
    private val logger: KotlinLogger = logger()
    private var envDriver: String = ""
    private var envUrl: String = ""
    private var envLogin: String = ""
    private var envPass: String = ""
    private var hibernateDialect = ""
    private val migrationLocation = "db/migration"

    init {
        val cfg = Configuration().configure("hibernate.cfg.xml")
        setEnv()
        migrate()
        val url = System.getenv("JDBC_DATABASE_URL")
        if (url != "" && url != null) {
            logger.info("Reconfiguring hibernate to use postgres")
            cfg.setProperty("hibernate.connection.url", url)
            cfg.setProperty("hibernate.connection.username", System.getenv("JDBC_DATABASE_USERNAME"))
            cfg.setProperty("hibernate.connection.password", System.getenv("JDBC_DATABASE_PASSWORD"))
            cfg.setProperty("hibernate.connection.driverClass", "org.postgresql.Driver")
        }
        sessionFactory = cfg.buildSessionFactory()

    }

    override fun get(): SessionFactory {
        return sessionFactory
    }

    private fun setEnv() {
        val url = System.getenv("JDBC_DATABASE_URL")
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
