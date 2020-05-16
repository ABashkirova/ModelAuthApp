package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.dao.ResourceDao
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

class ResourceRepository(@Inject private val dao: ResourceDao) {

    fun getResourcesByUserLogin(usersResource: UsersResources): DBAccess? {
        return dao.find(
            usersResource.login,
            usersResource.path.plus("."),
            usersResource.role.name
        )
    }
}
