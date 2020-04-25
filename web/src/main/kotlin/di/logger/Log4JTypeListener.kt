package di.logger

import com.google.inject.TypeLiteral
import com.google.inject.spi.TypeEncounter
import com.google.inject.spi.TypeListener
import org.apache.logging.log4j.kotlin.KotlinLogger

internal class Log4JTypeListener : TypeListener {
    override fun <T> hear(typeLiteral: TypeLiteral<T>, typeEncounter: TypeEncounter<T>) {
        var clazz: Class<*>? = typeLiteral.rawType
        while (clazz != null) {
            for (field in clazz.declaredFields) {
                if (field.type === KotlinLogger::class.java &&
                    field.isAnnotationPresent(InjectLogger::class.java)
                ) {
                    println("register Log4JMembersInjector(field)")
                    typeEncounter.register(Log4JMembersInjector(field))
                }
            }
            clazz = clazz.superclass
        }
    }
}
