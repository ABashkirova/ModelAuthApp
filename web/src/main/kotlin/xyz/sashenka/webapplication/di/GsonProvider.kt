package xyz.sashenka.webapplication.di

import com.google.gson.Gson
import javax.inject.Provider

class GsonProvider: Provider<Gson> {
    override fun get(): Gson {
        return Gson()
    }
}
