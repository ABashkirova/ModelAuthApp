package di.logger

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Target(FIELD)
@Retention(RUNTIME)
annotation class InjectLogger
