package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.di.HibernateProvider
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import javax.persistence.NoResultException

class UserDaoImpl : UserDao {
    @Inject lateinit var sessionProvider: HibernateProvider

    override fun save(user: DBUser) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()
        session.save(user)
        session.transaction.commit()
        session.close()
    }

    override fun getAll(): List<DBUser> {
        val session = sessionProvider.get().openSession()

        val query = session.createQuery("FROM DBUser", DBUser::class.java)
        val userList = query.resultList

        session.close()
        return userList
    }

    override fun findUserById(id: Int): DBUser? {
        val session = sessionProvider.get().openSession()
        val user = session.get(DBUser::class.java, id)
        session.close()
        return user
    }

    override fun findUser(login: String): DBUser? {
        val session = sessionProvider.get().openSession()

        val query = session.createQuery(
            "FROM DBUser WHERE login = '$login'",
            DBUser::class.java
        )
        val user: DBUser? = try {
            query.singleResult
        } catch (e: NoResultException) {
            null
        }
        session.close()
        return user
    }
}
