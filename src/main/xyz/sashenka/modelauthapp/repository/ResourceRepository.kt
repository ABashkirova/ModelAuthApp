package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.DBAccess

class ResourceRepository(private val dao: ResourceDAO) {
    fun getResourcesByUserLogin(usersResource: UsersResources): DBAccess? {
        return dao.requestAccessByResource(
            usersResource.login,
            usersResource.path.plus("."),
            usersResource.role.name
        )
    }
}