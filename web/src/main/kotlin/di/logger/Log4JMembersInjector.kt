package di.logger

import com.google.inject.MembersInjector
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import java.lang.reflect.Field

internal class Log4JMembersInjector<T>(private val field: Field) : MembersInjector<T> {
    private val logger: KotlinLogger by lazy { logger(field.declaringClass.name) }

    init {
        field.isAccessible = true
    }

    @Throws(RuntimeException::class)
    override fun injectMembers(t: T) {
        try {
            field.set(t, logger)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }
}
