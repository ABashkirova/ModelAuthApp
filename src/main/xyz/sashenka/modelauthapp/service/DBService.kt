package xyz.sashenka.modelauthapp.service

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import java.sql.Connection
import java.sql.DriverManager

class DBService {
    var connection: Connection
        private set

    init {
        val envUrl: String = System.getenv("DBURL") ?: "jdbc:h2:./db/AuthorizationApp"
        val envLogin: String = System.getenv("DBLOGIN") ?: "sa"
        val envPass: String = System.getenv("DBPASS") ?: ""
        val envDBFile: String = System.getenv("DBFILE") ?: "./AAA"
        val flyway = Flyway
            .configure()
            .dataSource(envUrl, envLogin, envPass)
            .locations("db/migration")
            .load()

        this.connection = DriverManager.getConnection(envUrl, envLogin, envPass)
    }


}