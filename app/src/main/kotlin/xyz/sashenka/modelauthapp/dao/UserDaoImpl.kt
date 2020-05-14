package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import com.google.inject.Provider
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class UserDaoImpl @Inject constructor(
    var entityManager: Provider<EntityManager>
) : UserDao {
    override fun save(user: DBUser) {
        entityManager.get().merge(user)
    }

    override fun getAll(): List<DBUser> {
        val criteriaQuery = entityManager.get().criteriaBuilder.createQuery(DBUser::class.java)
        val rootEntry: Root<DBUser> = criteriaQuery.from(DBUser::class.java)
        val all: CriteriaQuery<DBUser> = criteriaQuery.select(rootEntry)
        val allQuery: TypedQuery<DBUser> = entityManager.get().createQuery(all)
        return allQuery.resultList
    }

    override fun findUserById(id: Int): DBUser? {
        return entityManager.get().find(DBUser::class.java, id)
    }

    override fun findUser(login: String): DBUser? {
        val createQuery: CriteriaQuery<DBUser> =
            entityManager.get().criteriaBuilder.createQuery(DBUser::class.java)
        val root: Root<DBUser> = createQuery.from(DBUser::class.java)
        createQuery.where(root.get<Any>("login").`in`(login))
        return entityManager.get().createQuery(createQuery).singleResult
    }
}

