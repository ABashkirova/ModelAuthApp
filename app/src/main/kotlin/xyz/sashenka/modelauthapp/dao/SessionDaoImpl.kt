package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.di.HibernateProvider
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession

class SessionDaoImpl : SessionDao {
    @Inject
    lateinit var sessionProvider: HibernateProvider

    override fun save(userSession: DBUserSession) {
        val session = sessionProvider.get().openSession()
        session.beginTransaction()
        session.save(userSession)
        session.transaction.commit()
        session.close()
    }

    override fun getAll(): List<DBUserSession> {
        val session = sessionProvider.get().openSession()

        val query = session.createQuery("FROM DBUserSession ", DBUserSession::class.java)
        val userSessions = query.resultList

        session.close()
        return userSessions
    }

    override fun findById(id: Int): DBUserSession? {
        val session = sessionProvider.get().openSession()
        val userSession = session.get(DBUserSession::class.java, id)
        session.close()
        return userSession
    }

    override fun findByAccessId(accessId: Int): List<DBUserSession> {
        val session = sessionProvider.get().openSession()
        val query = session.createQuery(
            "FROM DBUserSession WHERE access_id = $accessId",
            DBUserSession::class.java
        )

        val userSessionList = query.resultList
        session.close()

        return userSessionList
    }
}
