package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.persist.jpa.JpaPersistModule
import xyz.sashenka.modelauthapp.dao.*
import java.util.HashMap

class DatabaseModule : AbstractModule() {
    val persistenceUnitName = "AaaPersistenceUnit"
    private val envDriver: String = System.getenv("JDBC_DATABASE_DRIVER") ?: "org.h2.Driver"
    private val envUrl: String = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:h2:file:./AAA"
    private val envLogin: String = System.getenv("JDBC_DATABASE_USERNAME") ?: "sa"
    private val envPass: String = System.getenv("JDBC_DATABASE_PASSWORD") ?: ""

    override fun configure() {
        super.configure()
        /*
         val jpaModule = JpaPersistModule(persistenceUnitName)
        jpaModule.properties(getDatabaseEnvironments())
        install(jpaModule)
         */
        val jpaPersistModule = JpaPersistModule("AaaPersistenceUnit")
        jpaPersistModule.properties(getDatabaseEnvironments())
        install(jpaPersistModule)
        bind(PersistenceInitializer::class.java).asEagerSingleton()
        bind(UserDao::class.java).to(UserDaoImpl::class.java).`in`(Singleton::class.java)
        bind(ResourceDao::class.java).to(ResourceDaoImpl::class.java).`in`(Singleton::class.java)
        bind(SessionDao::class.java).to(SessionDaoImpl::class.java).`in`(Singleton::class.java)
    }

    private fun getDatabaseEnvironments(): MutableMap<String, Any> {
        val envConfig: MutableMap<String, Any> = HashMap()
        envConfig["javax.persistence.jdbc.url"] = envUrl
        envConfig["javax.persistence.jdbc.user"] = envLogin
        envConfig["javax.persistence.jdbc.password"] = envPass
        envConfig["javax.persistence.jdbc.driver"] = envDriver
        return envConfig
    }
}
