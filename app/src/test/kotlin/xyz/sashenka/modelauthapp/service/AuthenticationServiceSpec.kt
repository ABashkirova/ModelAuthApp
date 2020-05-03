package xyz.sashenka.modelauthapp.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import kotlin.test.assertEquals
import kotlin.test.assertFalse

object AuthenticationServiceSpec : Spek({
    Feature("Authentication service") {
        lateinit var authService: AuthenticationService

        Scenario("user is exist") {
            val userLogin = "sasha"
            val userSasha = User(
                1,
                "sasha",
                "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
                "V9Me2nx"
            )
            val userRepositoryMock = mockk<UserRepository>()
            every {
                userRepositoryMock.getUserByLogin(userLogin)
            } returns userSasha

            Given("Set repository") {
                authService = AuthenticationService(userRepositoryMock)
            }

            Then("it should be exist") {
                val user = authService.findUser(userLogin)
                assertNotNull(user)
                assertEquals(userSasha, user)
                verify {
                    userRepositoryMock.getUserByLogin(userLogin)
                }
            }
        }

        Scenario("user is not exist") {
            val userRepositoryMock = mockk<UserRepository>()
            every {
                userRepositoryMock.getUserByLogin(ofType())
            } returns null

            Given("Set repository") {
                authService = AuthenticationService(userRepositoryMock)
            }

            Then("it should be not exist of vasya") {
                assertNull(authService.findUser("vasya"))
                verify {
                    userRepositoryMock.getUserByLogin(ofType())
                }
            }
        }

        Scenario("verify pass") {
            val user = User(
                1,
                "sasha",
                "bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3",
                "V9Me2nx"
            )
            Given("Set repository") {
                val userRepositoryMock = mockk<UserRepository>()
                authService = AuthenticationService(userRepositoryMock)
            }
            Then("it should be verified for sasha pass 123") {
                assertTrue(authService.verifyPass(user, "123"))
            }
            Then("it should be not verified for sasha pass 1234") {
                assertFalse(authService.verifyPass(user, "1234"))
            }
        }
    }
})
