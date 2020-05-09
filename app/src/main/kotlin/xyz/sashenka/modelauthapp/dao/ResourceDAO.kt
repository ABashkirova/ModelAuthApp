package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.service.db.DBService
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.ResultSet

class ResourceDAO(
    @Inject
    var dbService: DBService
) {
    private val selectUserResourcesByLoginSql: String
        get() = """
                SELECT ACCESS.USER_ID, ACCESS.RESOURCE, ACCESS.ROLE, ACCESS.ID
                FROM ACCESS
                LEFT JOIN USER 
                    ON ACCESS.USER_ID=USER.ID  
                WHERE 
                    USER.LOGIN=? 
                AND ACCESS.RESOURCE=SUBSTRING(?,1,LENGTH(ACCESS.RESOURCE)) 
                AND ACCESS.ROLE=?;
                """
    private val selectUserResourceByIdSql: String
        get() = "SELECT * FROM ACCESS WHERE ID = ?"
    private val selectUserResourceByUserIdSql: String
        get() = "SELECT * FROM ACCESS WHERE USER_ID = ?"
    private val selectAllUserResourcesSql: String
        get() = "SELECT * FROM ACCESS"

    private val id = "ID"
    private val userId = "USER_ID"
    private val resource = "RESOURCE"
    private val role = "ROLE"

    fun requestAccessByResource(login: String, resource: String, role: String): DBAccess? {
        dbService.provideConnect().use { connect ->
            val statement = connect.prepareStatement(selectUserResourcesByLoginSql)
            var access: DBAccess? = null
            statement.use {
                it.setValues(login, resource, role)
                it.executeQuery().use { value ->
                    if (value.next()) {
                        access = createAccess(value)
                    }
                }
            }
            return access
        }
    }

    fun requestAllAccesses(): List<DBAccess> {
        dbService.provideConnect().use { connect ->
            val listAccess: MutableList<DBAccess> = mutableListOf()
            val statement = connect.createStatement()
            statement.use {
                val resultSet = it.executeQuery(selectAllUserResourcesSql)
                while (resultSet.next()) {
                    listAccess.add(createAccess(resultSet))
                }
                return listAccess.toList()
            }
        }
    }

    fun requestAccessByUserId(userId: Int): List<DBAccess> {
        dbService.provideConnect().use { connect ->
            val listAccess: MutableList<DBAccess> = mutableListOf()
            val statement = connect.prepareStatement(selectUserResourceByUserIdSql)
            statement.use {
                it.setValues(userId)
                val resultSet = it.executeQuery()
                while (resultSet.next()) {
                    listAccess.add(createAccess(resultSet))
                }
                return listAccess.toList()
            }
        }
    }

    fun requestAccessById(id: Int): DBAccess? {
        dbService.provideConnect().use { connect ->
            var access: DBAccess? = null
            val statement = connect.prepareStatement(selectUserResourceByIdSql)
            statement.use {
                it.setValues(id)
                val resultSet = it.executeQuery()
                if (resultSet.next()) {
                    access = createAccess(resultSet)
                }
            }
            return access
        }
    }

    private fun createAccess(value: ResultSet): DBAccess {
        return DBAccess(
            id = value.getInt(id),
            userId = value.getInt(userId),
            resource = value.getString(resource),
            role = value.getString(role)
        )
    }
}
