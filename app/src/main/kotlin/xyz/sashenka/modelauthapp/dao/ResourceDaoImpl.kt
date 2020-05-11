package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class ResourceDaoImpl @Inject constructor(
    var entityManager: EntityManager
) : ResourceDao {
    private val selectUserResourcesByLoginSql: String
        get() = """
                SELECT ACCESS.USER_ID, ACCESS.RESOURCE, ACCESS.ROLE, ACCESS.ID
                FROM ACCESS
                LEFT JOIN USER 
                    ON ACCESS.USER_ID=USER.ID  
                WHERE 
                    USER.LOGIN=?1 
                AND ACCESS.RESOURCE=SUBSTRING(?2,1,LENGTH(ACCESS.RESOURCE)) 
                AND ACCESS.ROLE=?3
                """

    override fun save(user: DBAccess) {
        entityManager.merge(user)
    }

    override fun getAll(): List<DBAccess> {
        val criteriaQuery = entityManager.criteriaBuilder.createQuery(DBAccess::class.java)
        val rootEntry: Root<DBAccess> = criteriaQuery.from(DBAccess::class.java)
        val all: CriteriaQuery<DBAccess> = criteriaQuery.select(rootEntry)
        val allQuery: TypedQuery<DBAccess> = entityManager.createQuery(all)
        return allQuery.resultList
    }

    override fun findById(id: Int): DBAccess? {
        return entityManager.find(DBAccess::class.java, id)
    }

    override fun findByUserId(userId: Int): List<DBAccess> {
        val createQuery: CriteriaQuery<DBAccess> =
            entityManager.criteriaBuilder.createQuery(DBAccess::class.java)
        val root: Root<DBAccess> = createQuery.from(DBAccess::class.java)
        createQuery.where(root.get<Any>("userId").`in`(userId))
        return entityManager.createQuery(createQuery).resultList
    }

    override fun find(login: String, resource: String, role: String): DBAccess? {
        val query: TypedQuery<DBAccess> =
            entityManager.createQuery(selectUserResourcesByLoginSql, DBAccess::class.java)

        return query
            .setParameter(1, login)
            .setParameter(2, resource)
            .setParameter(3, role)
            .singleResult
    }
}
