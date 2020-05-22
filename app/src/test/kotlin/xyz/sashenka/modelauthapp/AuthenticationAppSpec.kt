package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object AuthenticationAppSpec : Spek({
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

    Feature("Authentication") {
        Scenario("return success") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
            }
            Then("Return code SUCCESS") {
                assertEquals(
                    ExitCode.SUCCESS,
                    app.run("-login sasha -pass qwerty".split(" ").toTypedArray())
                )
            }
        }

        Scenario("return wrong password") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns false
            }
            Then("Return code WRONG_PASSWORD") {
                assertEquals(
                    ExitCode.WRONG_PASSWORD,
                    app.run("-login sasha -pass qwerty".split(" ").toTypedArray())
                )
            }
        }

        Scenario("return unknown login") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns null
            }
            Then("Return code UNKNOWN_LOGIN") {
                assertEquals(
                    ExitCode.UNKNOWN_LOGIN,
                    app.run("-login sasha -pass qwerty".split(" ").toTypedArray())
                )
            }
        }
    }
})
