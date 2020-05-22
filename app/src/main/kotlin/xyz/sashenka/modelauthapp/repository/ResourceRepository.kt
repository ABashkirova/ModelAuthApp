package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

interface ResourceRepository {
    fun getResourcesByUserLogin(usersResource: UsersResources): DBAccess?
}
