package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.apache.logging.log4j.kotlin.loggerOf
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.di.Container
import xyz.sashenka.modelauthapp.model.ExitCode
import kotlin.test.assertEquals

object HelpApplicationSpec : Spek({
    val app = Application()

    lateinit var result: ExitCode
    Feature("Get Help") {
        Scenario("print help") {
            Given("App with args: -h") {
                result = app.run(arrayOf("-h"))
            }
            Then("Return code Help") {
                assertEquals(ExitCode.HELP, result)
            }
        }
    }
})
