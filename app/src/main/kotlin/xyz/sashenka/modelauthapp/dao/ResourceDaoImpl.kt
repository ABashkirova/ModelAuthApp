package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.di.HibernateProvider
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import javax.persistence.NoResultException

class ResourceDaoImpl : ResourceDao {
    @Inject lateinit var sessionProvider: HibernateProvider
    private val selectUserResourcesByLoginSql: String
        get() = """
                SELECT a
                FROM DBAccess a
                LEFT OUTER JOIN DBUser u
                    ON a.userId=u.id 
                WHERE 
                    u.login=?1 
                AND a.resource=SUBSTRING(?2,1,LENGTH(a.resource)) 
                AND a.role=?3
                """

    override fun save(user: DBAccess) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()
        session.save(user)
        session.transaction.commit()
        session.close()
    }

    override fun getAll(): List<DBAccess> {
        val session = sessionProvider.get().openSession()

        val query = session.createQuery("FROM DBAccess", DBAccess::class.java)
        val accesses = query.resultList

        session.close()
        return accesses
    }

    override fun findById(id: Int): DBAccess? {
        val session = sessionProvider.get().openSession()
        val access = session.get(DBAccess::class.java, id)
        session.close()
        return access
    }

    override fun findByUserId(userId: Int): List<DBAccess> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery(
            "FROM DBAccess WHERE user_id = $userId",
            DBAccess::class.java
        )

        val accessList = query.resultList
        session.close()
        return accessList
    }

    override fun find(login: String, resource: String, role: String): DBAccess? {
        val session = sessionProvider.get().openSession()
        val access = try {
            session.createQuery(
                """
                FROM DBAccess
                WHERE 
                user.login = '$login' 
                AND role = '$role' 
                AND resource=SUBSTRING('$resource',1,LENGTH(resource)) 
                """, DBAccess::class.java
            ).singleResult
        } catch (e: NoResultException) {
            null
        }
        session.close()
        return access
    }
}
