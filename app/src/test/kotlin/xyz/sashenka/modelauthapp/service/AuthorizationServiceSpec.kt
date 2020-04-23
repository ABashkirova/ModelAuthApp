package xyz.sashenka.modelauthapp.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.model.domain.Role
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import kotlin.test.assertEquals

object AuthorizationServiceSpec : Spek({
    lateinit var authService: AuthorizationService

    Feature("Authorisation service") {
        val usersResources = UsersResources("A", Role.READ, "sasha")
        val resourceRepositoryMock = mockk<ResourceRepository>()
        val expectedAccess = DBAccess(0, 0, "A", "READ")
        every {
            resourceRepositoryMock.getResourcesByUserLogin(usersResources)
        } returns expectedAccess

        Scenario("Check access") {
            Given("Set repository") {
                authService = AuthorizationService(resourceRepositoryMock)
            }

            Then("It should be access") {
                assertTrue(authService.checkAccess(usersResources))
                verify {
                    resourceRepositoryMock.getResourcesByUserLogin(usersResources)
                }
            }
        }

        Scenario("Check get resource access") {
            Given("Set repository") {
                authService = AuthorizationService(resourceRepositoryMock)
            }

            Then("It should be access") {
                val access = authService.getResourceAccess(usersResources)
                assertNotNull(access)
                assertEquals(expectedAccess, access)
                verify {
                    resourceRepositoryMock.getResourcesByUserLogin(usersResources)
                }
            }
        }
    }

    Feature("Authorisation service - no access") {
        val usersResources = UsersResources("A", Role.WRITE, "sasha")
        val resourceRepositoryMock = mockk<ResourceRepository>()
        every {
            resourceRepositoryMock.getResourcesByUserLogin(usersResources)
        } returns null

        Scenario("Check no access") {
            Given("Set repository") {
                authService = AuthorizationService(resourceRepositoryMock)
            }

            Then("It should be access") {
                assertFalse(authService.checkAccess(usersResources))
                verify {
                    resourceRepositoryMock.getResourcesByUserLogin(usersResources)
                }
            }
        }

        Scenario("Check get null access resource") {
            Given("Set repository") {
                authService = AuthorizationService(resourceRepositoryMock)
            }

            Then("It should non be access") {
                assertNull(authService.getResourceAccess(usersResources))
                verify {
                    resourceRepositoryMock.getResourcesByUserLogin(usersResources)
                }
            }
        }
    }
})
