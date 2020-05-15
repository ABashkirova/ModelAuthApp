package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import com.google.inject.Provider
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class ResourceDaoImpl @Inject constructor(
    var entityManager: Provider<EntityManager>
) : ResourceDao {
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
        entityManager.get().merge(user)
    }

    override fun getAll(): List<DBAccess> {
        val criteriaQuery = entityManager.get().criteriaBuilder.createQuery(DBAccess::class.java)
        val rootEntry: Root<DBAccess> = criteriaQuery.from(DBAccess::class.java)
        val all: CriteriaQuery<DBAccess> = criteriaQuery.select(rootEntry)
        val allQuery: TypedQuery<DBAccess> = entityManager.get().createQuery(all)
        return allQuery.resultList
    }

    override fun findById(id: Int): DBAccess? {
        return entityManager.get().find(DBAccess::class.java, id)
    }

    override fun findByUserId(userId: Int): List<DBAccess> {
        val createQuery: CriteriaQuery<DBAccess> =
            entityManager.get().criteriaBuilder.createQuery(DBAccess::class.java)
        val root: Root<DBAccess> = createQuery.from(DBAccess::class.java)
        createQuery.where(root.get<Any>("userId").`in`(userId))
        return entityManager.get().createQuery(createQuery).resultList
    }

    override fun find(login: String, resource: String, role: String): DBAccess? {
        val query: TypedQuery<DBAccess> =
            entityManager.get().createQuery(selectUserResourcesByLoginSql, DBAccess::class.java)

        return query
            .setParameter(1, login)
            .setParameter(2, resource)
            .setParameter(3, role)
            .singleResult
    }
}
