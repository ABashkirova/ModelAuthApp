package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object ApplicationSpec : Spek({
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()

    val user = User(
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock

    Feature("Invalid login") {
        Scenario("return invalid login format") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("123") } returns false
            }
            Then("Return code INVALID_LOGIN_FORMAT") {
                assertEquals(
                    ExitCode.INVALID_LOGIN_FORMAT,
                    app.run("-login 123 -pass qwerty".split(" ").toTypedArray())
                )
            }
        }
    }

    Feature("Invalid role") {
        Scenario("return unknown role") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns false
            }
            Then("Return code UNKNOWN_ROLE") {
                assertEquals(
                    ExitCode.UNKNOWN_ROLE,
                    app.run("-login sasha -pass qwerty -role Delete -res A".split(" ").toTypedArray())
                )
                verifyOrder {
                    validationServiceMock.isLoginValid("sasha")
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                    validationServiceMock.isRoleValid(ofType())
                }
            }
        }
    }
})
