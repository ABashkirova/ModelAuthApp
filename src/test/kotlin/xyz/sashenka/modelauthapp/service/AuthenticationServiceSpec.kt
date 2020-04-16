package xyz.sashenka.modelauthapp.service

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import kotlin.test.assertEquals

object AuthenticationServiceSpec : Spek({
    val users = listOf(
        User(
            "sasha",
            "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
            "V9Me2nx"
        ),
        User(
            "admin",
            "e0feb157dadff817c0f11b48d0441e56b475a27289117c6cb1ca7fd0b108b13c",
            "6xInN0l"
        ),
        User(
            "q",
            "2002c9e01093b6d8b7d3699d6b7bd1a5fb8200340b1275f52ae5794d59854849",
            "4bxdOU7"
        ),
        User(
            "abcdefghij",
            "d880929e469c4a2c19352f76460853be52ee581f7fcdd3097f86f670f690e910",
            "TM36tOy"
        )
    )

    val userDaoMock = mockk<UserDAO>()
    every {
        userDaoMock.requestUserByLogin(ofType())
    } answers {
        users.find { it.login == firstArg() }
    }

    Feature("Authentication service") {
        lateinit var authService: AuthenticationService

        Scenario("user is exist") {
            Given("Set repository") {
                val userRepository = UserRepository(userDaoMock)
                authService = AuthenticationService(userRepository)
            }

            Then("it should be exist of sasha") {
                val user = authService.findUser("sasha")
                assertNotNull(user)
                assertEquals("sasha", user?.login)
            }

            Then("it should be exist of admin") {
                val user = authService.findUser("admin")
                assertNotNull(user)
                assertEquals("admin", user?.login)
            }

            Then("it should be exist of abcdefghij") {
                val user = authService.findUser("abcdefghij")
                assertNotNull(user)
                assertEquals("abcdefghij", user?.login)
            }

            Then("it should be exist of q") {
                val user = authService.findUser("q")
                assertNotNull(user)
                assertEquals("q", user?.login)
            }

            Then("it should be not exist of q") {
                assertNull(authService.findUser("vasya"))
            }
        }

        Scenario("verify pass") {
            Given("Set repository") {
                val userRepository = UserRepository(userDaoMock)
                authService = AuthenticationService(userRepository)
            }

            Then("it should be verified for sasha") {
                val user = authService.findUser("sasha")
                assertNotNull(user)
                assertTrue(authService.verifyPass(user!!, "123"))
            }
        }
    }
})
