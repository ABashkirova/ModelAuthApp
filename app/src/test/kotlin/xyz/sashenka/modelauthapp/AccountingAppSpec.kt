package xyz.sashenka.modelauthapp

import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.controller.ArgHandler
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
    val argHandlerMock = mockk<ArgHandler>()
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()
    val authorizationServiceMock = mockk<AuthorizationService>()
    val accountingServiceMock = mockk<AccountingService>()

    val user = User(
        "sasha",
        "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
        "V9Me2nx"
    )
    val authenticationData =
        AuthenticationData("sasha", "qwerty")
    val authorizationData =
        AuthorizationData("A", "READ", "sasha")
    val accountingData = AccountingData(
        "sasha",
        "A",
        "2000-01-15",
        "2000-02-15",
        "10"
    )
    val access = DBAccess(0, DBUser(), "A", "READ")

    val app = Application()
    app.validatingService = validationServiceMock
    app.authenticationService = authenticationServiceMock
    app.authorizationService = authorizationServiceMock
    app.accountingService = accountingServiceMock

    lateinit var result: ExitCode

    Feature("Accounting") {
        Scenario("return success") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns true
                every { authorizationServiceMock.getResourceAccess(ofType()) } returns access
                every { accountingServiceMock.saveSession(ofType(), ofType()) } returns Unit
            }
            Then("Return code SUCCESS") {
                assertEquals(ExitCode.SUCCESS, result)
            }
        }

        Scenario("return NO_ACCESS") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns true
                every { authorizationServiceMock.getResourceAccess(ofType()) } returns null
            }
            Then("Return code NO_ACCESS") {
                assertEquals(ExitCode.NO_ACCESS, result)
            }
        }

        Scenario("return DI_ERROR") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns true
            }
            Then("Return code DI_ERROR") {
                assertEquals(ExitCode.DI_ERROR, result)
            }
        }

        Scenario("return DI_ERROR") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns true
            }
            Then("Return code DI_ERROR") {
                assertEquals(ExitCode.DI_ERROR, result)
            }
        }

        Scenario("invalid activity: invalide volume") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns true
                every { validationServiceMock.isVolumeValid(ofType()) } returns false
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(ExitCode.INVALID_ACTIVITY, result)
            }
        }

        Scenario("invalid activity: invalide dates (date start > date end") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns 10
                every { validationServiceMock.areDatesValid(ofType(), ofType()) } returns false
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(ExitCode.INVALID_ACTIVITY, result)
            }
        }

        Scenario("invalid activity: parse volume error") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns Date()
                every { validationServiceMock.parseVolume(ofType()) } returns null
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(ExitCode.INVALID_ACTIVITY, result)
            }
        }

        Scenario("invalid activity: parse date error") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A -ds 2000-01-15 -de 2000-02-15") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns true
                every { argHandlerMock.getAccountingData() } returns accountingData
                every { validationServiceMock.parseDate(ofType()) } returns null
            }
            Then("Return code INVALID_ACTIVITY") {
                assertEquals(ExitCode.INVALID_ACTIVITY, result)
            }
        }
    }
})
