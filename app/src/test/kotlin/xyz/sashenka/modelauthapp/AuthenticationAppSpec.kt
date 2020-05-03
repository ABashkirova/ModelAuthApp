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
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.AuthenticationData
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object AuthenticationAppSpec : Spek({
    lateinit var app: Application
    val containerMock = mockk<Container>()
    val argHandlerMock = mockk<ArgHandler>()
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()

    val user = User(
        1,
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val authenticationData = AuthenticationData("sasha", "qwerty")

    every { containerMock.getLogger(Application::class.java) } returns loggerOf(ApplicationSpec::class.java)

    Feature("Authentication") {
        Scenario("return success") {
            Given("App with args: -login sasha -pass qwerty") {
                app = Application(arrayOf("-login sasha -pass qwerty"), containerMock)
            }
            When("container init service") {

                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { containerMock.getAuthenticationService() } returns authenticationServiceMock
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns null
            }
            Then("Return code SUCCESS") {
                assertEquals(ExitCode.SUCCESS, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    containerMock.getAuthenticationService()
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                    argHandlerMock.getAuthorizationData()
                }
            }
        }

        Scenario("return wrong password") {
            Given("App with args: -login sasha -pass qwerty") {
                app = Application(arrayOf("-login sasha -pass qwerty"), containerMock)
            }
            When("container init service") {
                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { containerMock.getAuthenticationService() } returns authenticationServiceMock
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns false
            }
            Then("Return code WRONG_PASSWORD") {
                assertEquals(ExitCode.WRONG_PASSWORD, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    containerMock.getAuthenticationService()
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                }
            }
        }

        Scenario("return unknown login") {
            Given("App with args: -login sasha -pass qwerty") {
                app = Application(arrayOf("-login sasha -pass qwerty"), containerMock)
            }
            When("container init service") {
                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { containerMock.getAuthenticationService() } returns authenticationServiceMock
                every { authenticationServiceMock.findUser(ofType()) } returns null
            }
            Then("Return code UNKNOWN_LOGIN") {
                assertEquals(ExitCode.UNKNOWN_LOGIN, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    containerMock.getAuthenticationService()
                    authenticationServiceMock.findUser(ofType())
                }
            }
        }

        Scenario("return di error") {
            Given("App with args: -login sasha -pass qwerty") {
                app = Application(arrayOf("-login sasha -pass qwerty"), containerMock)
            }
            When("container init service") {
                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { containerMock.getAuthenticationService() } returns null
            }
            Then("Return code UNKNOWN_LOGIN") {
                assertEquals(ExitCode.DI_ERROR, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    containerMock.getAuthenticationService()
                }
            }
        }
    }
})
