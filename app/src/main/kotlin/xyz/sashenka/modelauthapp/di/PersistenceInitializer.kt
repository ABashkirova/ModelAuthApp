package xyz.sashenka.modelauthapp.di

import com.google.inject.Inject
import com.google.inject.persist.PersistService

class PersistenceInitializer @Inject constructor(service: PersistService) {
    init {
        service.start()
    }
}
