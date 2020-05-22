package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object AuthorizationAppSpec : Spek({
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()
    val authorizationServiceMock = mockk<AuthorizationService>()

    val user = DBUser(
        0,
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock

    app.authorizationService = authorizationServiceMock

    Feature("Authorization") {
        Scenario("return success") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { authorizationServiceMock.getResourceAccess(ofType()) } returns DBAccess()
            }
            Then("Return code SUCCESS") {
                assertEquals(
                    ExitCode.SUCCESS,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A"
                            .split(" ")
                            .toTypedArray()
                    ).exitCode
                )
            }
        }

        Scenario("return no access") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns false
            }
            Then("Return code NO_ACCESS") {
                assertEquals(
                    ExitCode.NO_ACCESS,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A"
                            .split(" ")
                            .toTypedArray()
                    ).exitCode
                )
            }
        }
    }
})
