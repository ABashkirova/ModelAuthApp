package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.persist.jpa.JpaPersistModule
import org.flywaydb.core.Flyway
import xyz.sashenka.modelauthapp.dao.*
import java.net.URI
import java.util.*

class DatabaseModule : AbstractModule() {
    private val persistenceUnitName = "AaaPersistenceUnit"
    private var envDriver: String = ""
    private var envUrl: String = ""
    private var envLogin: String = ""
    private var envPass: String = ""
    private val migrationLocation = "db/migration"

    override fun configure() {
        super.configure()
        setEnv()
        val jpaModule = JpaPersistModule(persistenceUnitName)
        jpaModule.properties(getDatabaseEnvironments())
        install(jpaModule)
        bind(PersistenceInitializer::class.java).asEagerSingleton()
        bind(UserDao::class.java).to(UserDaoImpl::class.java).`in`(Singleton::class.java)
        bind(ResourceDao::class.java).to(ResourceDaoImpl::class.java).`in`(Singleton::class.java)
        bind(SessionDao::class.java).to(SessionDaoImpl::class.java).`in`(Singleton::class.java)
        migrate()
    }

    private fun getDatabaseEnvironments(): MutableMap<String, Any> {
        val envConfig: MutableMap<String, Any> = HashMap()
        envConfig["javax.persistence.jdbc.url"] = envUrl
        envConfig["javax.persistence.jdbc.user"] = envLogin
        envConfig["javax.persistence.jdbc.password"] = envPass
        envConfig["javax.persistence.jdbc.driver"] = envDriver
        return envConfig
    }

    private fun setEnv() {
        val url = System.getenv("DATABASE_URL")
        if (url.isNullOrEmpty()) {
            envUrl = "jdbc:h2:file:./AAA"
            envLogin = "sa"
            envPass = ""
            envDriver = "org.h2.Driver"
        } else {
            envUrl = System.getenv("JDBC_DATABASE_URL")
            envLogin = System.getenv("JDBC_DATABASE_USERNAME")
            envPass = System.getenv("JDBC_DATABASE_PASSWORD")
            envDriver = "org.postgresql.Driver"
        }
    }

    private fun migrate() {
        Flyway
            .configure()
            .dataSource(envUrl, envLogin, envPass)
            .locations(migrationLocation)
            .load()
            .migrate()
    }
}
