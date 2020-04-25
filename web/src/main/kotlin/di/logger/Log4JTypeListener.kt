package di.logger

import com.google.inject.TypeLiteral
import com.google.inject.spi.TypeEncounter
import com.google.inject.spi.TypeListener
import org.apache.logging.log4j.kotlin.KotlinLogger

internal class Log4JTypeListener : TypeListener {
    override fun <T> hear(typeLiteral: TypeLiteral<T>, typeEncounter: TypeEncounter<T>) {
        var clazz = typeLiteral.rawType
        while (clazz != null) {
            clazz.declaredFields
                .asSequence()
                .filter { it.type === KotlinLogger::class.java && it.isAnnotationPresent(InjectLogger::class.java) }
                .forEach { typeEncounter.register(Log4JMembersInjector(it)) }
            clazz = clazz.superclass
        }
    }
}
