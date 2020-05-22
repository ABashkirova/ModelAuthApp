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
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object AuthorizationAppSpec : Spek({
    val argHandlerMock = mockk<ArgHandler>()
    val validationServiceMock = mockk<ValidatingService>()
    val authenticationServiceMock = mockk<AuthenticationService>()
    val authorizationServiceMock = mockk<AuthorizationService>()

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
    app.authorizationService = authorizationServiceMock

    lateinit var result: ExitCode

    Feature("Authorization") {
        Scenario("return success") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A") {
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
                every { argHandlerMock.getAccountingData() } returns null
            }
            Then("Return code SUCCESS") {
                assertEquals(ExitCode.SUCCESS, result)
            }
        }

        Scenario("return no access") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
                every { authorizationServiceMock.checkAccess(ofType()) } returns false
            }
            Then("Return code NO_ACCESS") {
                assertEquals(ExitCode.NO_ACCESS, result)
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

        Scenario("return di error") {
            Given("App with args: -login sasha -pass qwerty -role READ -res A") {
                result = app.run(arrayOf("-login sasha -pass qwerty -role READ -res A"))
            }
            When("container init service") {
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns true
            }
            Then("Return code DI_ERROR") {
                assertEquals(ExitCode.DI_ERROR, result)
            }
        }
    }
})
