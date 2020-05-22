package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.args.AuthenticationData
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object AuthenticationAppSpec : Spek({
    val argHandlerMock = mockk<ArgHandler>()
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()

    val user = User(
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val authenticationData =
        AuthenticationData("sasha", "qwerty")

    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock

    lateinit var result: ExitCode

    Feature("Authentication") {
        Scenario("return success") {
            Given("App with args: -login sasha -pass qwerty") {
                result = app.run(arrayOf("-login sasha -pass qwerty"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns null
            }
            Then("Return code SUCCESS") {
                assertEquals(ExitCode.SUCCESS, result)
                verifyOrder {
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                    argHandlerMock.getAuthorizationData()
                }
            }
        }

        Scenario("return wrong password") {
            Given("App with args: -login sasha -pass qwerty") {
                result = app.run(arrayOf("-login sasha -pass qwerty"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns false
            }
            Then("Return code WRONG_PASSWORD") {
                assertEquals(ExitCode.WRONG_PASSWORD, result)
                verifyOrder {
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                }
            }
        }

        Scenario("return unknown login") {
            Given("App with args: -login sasha -pass qwerty") {
                result = app.run(arrayOf("-login sasha -pass qwerty"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns null
            }
            Then("Return code UNKNOWN_LOGIN") {
                assertEquals(ExitCode.UNKNOWN_LOGIN, result)
                verifyOrder {
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    authenticationServiceMock.findUser(ofType())
                }
            }
        }

        Scenario("return di error") {
            Given("App with args: -login sasha -pass qwerty") {
                result = app.run(arrayOf("-login sasha -pass qwerty"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
            }
            Then("Return code UNKNOWN_LOGIN") {
                assertEquals(ExitCode.DI_ERROR, result)
                verifyOrder {
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                }
            }
        }
    }
})
