package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.args.AccountingData
import xyz.sashenka.modelauthapp.model.dto.args.AuthenticationData
import xyz.sashenka.modelauthapp.model.dto.args.AuthorizationData
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import xyz.sashenka.modelauthapp.service.AccountingService
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import java.util.Date
import kotlin.test.assertEquals

object AccountingAppSpec : Spek({
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()
    val authorizationServiceMock = mockk<AuthorizationService>()
    val accountingServiceMock = mockk<AccountingService>()

    val user = User(
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock
    app.authorizationService = authorizationServiceMock
    app.accountingService = accountingServiceMock

    Feature("Accounting") {
        Scenario("return success") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
            }
            Then("Return code SUCCESS") {
                assertEquals(
                    ExitCode.SUCCESS,
                    app.run("-login sasha -pass qwerty -role READ -res A".split(" ").toTypedArray())
                )
            }
        }

        Scenario("return NO_ACCESS") {
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
                    app.run("-login sasha -pass qwerty -role READ -res A".split(" ").toTypedArray())
                )
            }
        }

        Scenario("invalid activity: invalide volume") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns -1
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns false
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(
                    ExitCode.INVALID_ACTIVITY,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol -1".split(" ")
                            .toTypedArray()
                    )
                )
            }
        }

        Scenario("invalid activity: invalide dates (date start > date end") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns false
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(
                    ExitCode.INVALID_ACTIVITY,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-01-10 -vol 10"
                            .split(" ")
                            .toTypedArray()
                    )
                )
            }
        }

        Scenario("invalid activity: parse volume error") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns null
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(
                    ExitCode.INVALID_ACTIVITY,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol 10.1".split(" ")
                            .toTypedArray()
                    )
                )
            }
        }

        Scenario("invalid activity: parse date error") {
            When("container init service") {
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { validationServiceMock.parseDate(ofType()) } returns null
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(
                    ExitCode.INVALID_ACTIVITY,
                    app.run(
                        "-login sasha -pass qwerty -role READ -res A -ds 2000-00-15 -de 2000-02-15 -vol 10".split(" ")
                            .toTypedArray()
                    )
                )
            }
        }
    }
})
