package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

interface AuthorizationService {
    fun checkAccess(usersResource: UsersResources): Boolean
    fun getResourceAccess(usersResource: UsersResources): DBAccess?
}
