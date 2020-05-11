package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class UserDaoImpl @Inject constructor(
    var entityManager: EntityManager
) : UserDao {
    override fun save(user: DBUser) {
        entityManager.merge(user)
    }

    override fun getAll(): List<DBUser> {
        val criteriaQuery = entityManager.criteriaBuilder.createQuery(DBUser::class.java)
        val rootEntry: Root<DBUser> = criteriaQuery.from(DBUser::class.java)
        val all: CriteriaQuery<DBUser> = criteriaQuery.select(rootEntry)
        val allQuery: TypedQuery<DBUser> = entityManager.createQuery(all)
        return allQuery.resultList
    }

    override fun findUserById(id: Int): DBUser? {
        return entityManager.find(DBUser::class.java, id)
    }

    override fun findUser(login: String): DBUser? {
        val createQuery: CriteriaQuery<DBUser> =
            entityManager.criteriaBuilder.createQuery(DBUser::class.java)
        val root: Root<DBUser> = createQuery.from(DBUser::class.java)
        createQuery.where(root.get<Any>("login").`in`(login))
        return entityManager.createQuery(createQuery).singleResult
    }
}

