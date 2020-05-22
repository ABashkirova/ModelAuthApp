package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.ResourceDao
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

@Singleton
class ResourceRepositoryImpl : ResourceRepository {
    @Inject
    lateinit var dao: ResourceDao

    override fun getResourcesByUserLogin(usersResource: UsersResources): DBAccess? {
        return dao.find(
            usersResource.login,
            usersResource.path.plus("."),
            usersResource.role.name
        )
    }
}
