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
    lateinit var app: Application
    val containerMock = mockk<Container>()
    val argHandlerMock = mockk<ArgHandler>()

    every { containerMock.getLogger(Application::class.java) } returns loggerOf(ApplicationSpec::class.java)

    Feature("Get Help") {
        Scenario("print help") {
            Given("App with args: -h") {
                app = Application(arrayOf("-h"), containerMock)
            }
            When("container init service") {
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns null
                every { containerMock.getHelpService().printHelp() } returns Unit
            }
            Then("Return code Help") {
                assertEquals(ExitCode.HELP, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    containerMock.getHelpService().printHelp()
                }
            }
        }
    }
})
