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
import xyz.sashenka.modelauthapp.model.dto.args.AuthorizationData
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object ApplicationSpec : Spek({
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
    val authorizationData =
        AuthorizationData("A", "READ", "sasha")

    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock

    lateinit var result: ExitCode
    Feature("Invalid login") {
        Scenario("return invalid login format") {
            Given("App with args: -login 123 -pass qwerty") {
                result = app.run(arrayOf("-login 123 -pass qwerty"))
            }
            When("container init service") {
               every { argHandlerMock.getAuthenticationData() } returns AuthenticationData(
                    "123",
                    "qwerty"
                )
                every { validationServiceMock.isLoginValid("123") } returns false
            }
            Then("Return code INVALID_LOGIN_FORMAT") {
                assertEquals(ExitCode.INVALID_LOGIN_FORMAT, result)
                verifyOrder {

                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("123")
                }
            }
        }
    }

    Feature("Invalid role") {
        Scenario("return unknown role") {
            Given("App with args: -login sasha -pass qwerty -role Delete -res A") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role Delete -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns false
            }
            Then("Return code UNKNOWN_ROLE") {
                assertEquals(ExitCode.UNKNOWN_ROLE, result)
                verifyOrder {
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                    argHandlerMock.getAuthorizationData()
                    validationServiceMock.isRoleValid(ofType())
                }
            }
        }
    }
})
