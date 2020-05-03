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
import xyz.sashenka.modelauthapp.model.dto.args.AuthenticationData
import xyz.sashenka.modelauthapp.model.dto.args.AuthorizationData
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.ValidatingService
import kotlin.test.assertEquals

object ApplicationSpec : Spek({
    lateinit var app: Application
    val containerMock = mockk<Container>()
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

    every { containerMock.getLogger(Application::class.java) } returns loggerOf(ApplicationSpec::class.java)

    Feature("Invalid login") {
        Scenario("return invalid login format") {
            Given("App with args: -login 123 -pass qwerty") {
                app = Application(arrayOf("-login 123 -pass qwerty"), containerMock)
            }
            When("container init service") {
                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns AuthenticationData(
                    "123",
                    "qwerty"
                )
                every { validationServiceMock.isLoginValid("123") } returns false
            }
            Then("Return code INVALID_LOGIN_FORMAT") {
                assertEquals(ExitCode.INVALID_LOGIN_FORMAT, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    containerMock.getValidatingService()
                    validationServiceMock.isLoginValid("123")
                }
            }
        }
    }

    Feature("Invalid role") {
        Scenario("return unknown role") {
            Given("App with args: -login sasha -pass qwerty -role Delete -res A") {
                app = Application(arrayOf("-login sasha -pass qwerty -role Delete -res A"), containerMock)
            }
            When("container init service") {

                every { containerMock.getValidatingService() } returns validationServiceMock
                every { containerMock.getArgHandler(ofType()) } returns argHandlerMock
                every { argHandlerMock.getAuthenticationData() } returns authenticationData
                every { validationServiceMock.isLoginValid("sasha") } returns true
                every { containerMock.getAuthenticationService() } returns authenticationServiceMock
                every { authenticationServiceMock.findUser(ofType()) } returns user
                every { authenticationServiceMock.verifyPass(ofType(), ofType()) } returns true
                every { argHandlerMock.getAuthorizationData() } returns authorizationData
                every { validationServiceMock.isRoleValid(ofType()) } returns false
            }
            Then("Return code UNKNOWN_ROLE") {
                assertEquals(ExitCode.UNKNOWN_ROLE, app.run())
                verifyOrder {
                    containerMock.getArgHandler(ofType())
                    argHandlerMock.getAuthenticationData()
                    validationServiceMock.isLoginValid("sasha")
                    containerMock.getAuthenticationService()
                    authenticationServiceMock.findUser(ofType())
                    authenticationServiceMock.verifyPass(ofType(), ofType())
                    argHandlerMock.getAuthorizationData()
                    validationServiceMock.isRoleValid(ofType())
                }
            }
        }
    }
})
