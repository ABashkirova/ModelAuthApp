package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class UserDaoImpl @Inject constructor(
    var entityManager: EntityManager
) : UserDao {
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
}

