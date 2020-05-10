package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import com.google.inject.Provider
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import java.sql.ResultSet
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class UserDaoImpl: UserDao {
    @Inject
    private lateinit var entityManager: Provider<EntityManager>
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"
    private val userByIdSql: String
        get() = "SELECT * FROM USER WHERE ID=?;"
    private val allUsersSql: String
        get() = "SELECT * FROM USER"

    private val id = "ID"
    private val login = "LOGIN"
    private val hashPassword = "HASH_PASSWORD"
    private val salt = "SALT"

    fun requestAllUsers(): List<DBUser> {
        return getAll()
//        dbService.provideConnect().use { connect ->
//            val statement = connect.createStatement()
//            val result: MutableList<DBUser> = mutableListOf()
//            statement.use {
//                val resultSet = statement.executeQuery(allUsersSql)
//                while (resultSet.next()) {
//                    result.add(createUser(resultSet))
//                }
//            }
//            return result.toList()
//        }
    }

    fun requestUserByLogin(login: String): User? {
        return findUser(login)?.toPlain()
            //requestUser(userByLoginSql, login)?.toPlain()
    }

    fun requestUserById(id: Int): DBUser? {
        return findUserById(id)
        //requestUser(userByIdSql, id)
    }

    override fun save(user: DBUser) {
        entityManager.get().merge(user)
    }

    override fun getAll(): List<DBUser> {
        val createQuery: CriteriaQuery<DBUser> =
            entityManager.get().criteriaBuilder.createQuery(DBUser::class.java)
        return entityManager.get().createQuery(createQuery).resultList
    }

    override fun findUserById(id: Int): DBUser? {
        return entityManager.get().find(DBUser::class.java, id)
    }

    override fun findUser(login: String): DBUser? {
        val createQuery: CriteriaQuery<DBUser> =
            entityManager.get().criteriaBuilder.createQuery(DBUser::class.java)
        val root: Root<DBUser> = createQuery.from(DBUser::class.java)
        createQuery.where(root.get<Any>("name").`in`(login))
        return entityManager.get().createQuery(createQuery).singleResult
    }

    private fun createUser(value: ResultSet): DBUser {
        return DBUser(
            value.getInt(id),
            value.getString(login),
            value.getString(hashPassword),
            value.getString(salt)
        )
    }

//    private fun requestUser(sql: String, parameter: Any): DBUser? {
//        dbService.provideConnect().use { connect ->
//            val statement = connect.prepareStatement(sql)
//            return statement.use {
//                it.setValues(parameter)
//                return@use it.executeQuery().use { value ->
//                    return@use when {
//                        value.next() -> createUser(value)
//                        else -> null
//                    }
//                }
//            }
//            return null
//        }
//    }
}

