package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import com.google.inject.Provider
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession
import java.sql.Date
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class SessionDaoImpl @Inject constructor(
    var entityManager: Provider<EntityManager>
) : SessionDao {
    override fun save(access: DBAccess, session: UserSession) {
        entityManager.get().merge(
            DBUserSession(
                accessId = access.id,
                dateStart = Date.valueOf(session.dateStart),
                dateEnd = Date.valueOf(session.dateEnd),
                volume = session.volume
            )
        )
    }

    override fun getAll(): List<DBUserSession> {
        val criteriaQuery= entityManager.get().criteriaBuilder.createQuery(DBUserSession::class.java)
        val rootEntry: Root<DBUserSession> = criteriaQuery.from(DBUserSession::class.java)
        val all: CriteriaQuery<DBUserSession> = criteriaQuery.select(rootEntry)
        val allQuery: TypedQuery<DBUserSession> = entityManager.get().createQuery(all)
        return allQuery.resultList
    }

    override fun findById(id: Int): DBUserSession? {
        return entityManager.get().find(DBUserSession::class.java, id)
    }

    override fun findByAccessId(accessId: Int): List<DBUserSession> {
        val createQuery: CriteriaQuery<DBUserSession> =
            entityManager.get().criteriaBuilder.createQuery(DBUserSession::class.java)
        val root: Root<DBUserSession> = createQuery.from(DBUserSession::class.java)
        createQuery.where(root.get<Any>("accessId").`in`(accessId))
        return entityManager.get().createQuery(createQuery).resultList
    }
}
