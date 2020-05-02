package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection

class ResourceDAO(private val dbConnection: Connection) {
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
        get() = "SELECT * FROM ACCESS WHERE ID = ?"
    private val selectAllUserResourcesSql: String
        get() = "SELECT * FROM ACCESS"

    fun requestAccessByResource(login: String, resource: String, role: String): DBAccess? {
        val statement = dbConnection.prepareStatement(selectUserResourcesByLoginSql)
        return statement.use {
            it.setValues(login, resource, role)
            return@use it.executeQuery().use { value ->
                return@use if (value.next()) {

                    DBAccess(
                        id = value.getInt("ID"),
                        userId = value.getInt("USER_ID"),
                        resource = value.getString("RESOURCE"),
                        role = value.getString("ROLE")
                    )
                } else null
            }
        }
    }

    fun requestAllAccesses(): List<DBAccess> {
        val result: MutableList<DBAccess> = mutableListOf()
        val statement = dbConnection.createStatement()
        statement.use {
            val resultSet = it.executeQuery(selectAllUserResourcesSql)
            while (resultSet.next()) {
                result.add(
                    DBAccess(
                        id = resultSet.getInt("ID"),
                        userId = resultSet.getInt("USER_ID"),
                        resource = resultSet.getString("RESOURCE"),
                        role = resultSet.getString("ROLE")
                    )
                )
            }
            return result.toList()
        }
    }
    fun requestAccessByUserId(userId: Int): List<DBAccess> {
        val result: MutableList<DBAccess> = mutableListOf()
        val statement = dbConnection.prepareStatement(selectUserResourceByUserIdSql)
        statement.use {
            it.setValues(userId)
            val resultSet = it.executeQuery()
            while (resultSet.next()) {
                result.add(
                    DBAccess(
                        id = resultSet.getInt("ID"),
                        userId = resultSet.getInt("USER_ID"),
                        resource = resultSet.getString("RESOURCE"),
                        role = resultSet.getString("ROLE")
                    )
                )
            }
            return result.toList()
        }
    }

    fun requestAccessById(id: Int): DBAccess? {
        var result: DBAccess? = null
        val statement = dbConnection.prepareStatement(selectUserResourceByIdSql)
        statement.use {
            it.setValues(id)
            val resultSet = it.executeQuery()
            if (resultSet.next()) {
                result = DBAccess(
                    id = resultSet.getInt("ID"),
                    userId = resultSet.getInt("USER_ID"),
                    resource = resultSet.getString("RESOURCE"),
                    role = resultSet.getString("ROLE")
                )
            }
        }
        return result
    }

}
