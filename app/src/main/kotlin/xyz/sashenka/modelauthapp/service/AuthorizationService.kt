package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.repository.ResourceRepository

class AuthorizationService(
    private var resourceRepository: ResourceRepository
) {
    fun checkAccess(usersResource: UsersResources): Boolean {
        val access = resourceRepository.getResourcesByUserLogin(usersResource)
        return access != null
    }

    fun getResourceAccess(usersResource: UsersResources): DBAccess? =
        resourceRepository.getResourcesByUserLogin(usersResource)
}
