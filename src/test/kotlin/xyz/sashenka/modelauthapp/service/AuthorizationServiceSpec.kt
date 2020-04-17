package xyz.sashenka.modelauthapp.service

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.model.domain.Role
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.repository.ResourceRepository

object AuthorizationServiceSpec : Spek({

    Feature("Authorisation service") {
        val resourceDaoMock = mockk<ResourceDAO>()
        every {
            resourceDaoMock.requestAccessByResource(ofType(), ofType(), ofType())
        } returns DBAccess(0, 0, "res", "role")

        Scenario("Check access") {
            lateinit var authService: AuthorizationService

            val usersResources = UsersResources("A", Role.READ, "sasha")
            Given("Set repository") {
                val resourceRepository = ResourceRepository(resourceDaoMock)
                authService = AuthorizationService(resourceRepository)
            }

            Then("It should be access") {
                assertTrue(authService.checkAccess(usersResources))
            }
        }

        Scenario("Check access") {
            lateinit var authService: AuthorizationService

            val usersResources = UsersResources("A", Role.READ, "sasha")
            Given("Set repository") {
                val resourceRepository = ResourceRepository(resourceDaoMock)
                authService = AuthorizationService(resourceRepository)
            }

            Then("It should be access") {
                assertNotNull(authService.getResourceAccess(usersResources))
            }
        }
    }

    Feature("Authorisation service - no access") {
        val resourceDaoMock = mockk<ResourceDAO>()
        every {
            resourceDaoMock.requestAccessByResource(ofType(), ofType(), ofType())
        } returns null

        Scenario("Check no access") {
            lateinit var authService: AuthorizationService

            val usersResources = UsersResources("A", Role.WRITE, "sasha")
            Given("Set repository") {
                val resourceRepository = ResourceRepository(resourceDaoMock)
                authService = AuthorizationService(resourceRepository)
            }

            Then("It should be access") {
                assertFalse(authService.checkAccess(usersResources))
            }
        }

        Scenario("Check no access") {
            lateinit var authService: AuthorizationService

            val usersResources = UsersResources("A", Role.WRITE, "sasha")
            Given("Set repository") {
                val resourceRepository = ResourceRepository(resourceDaoMock)
                authService = AuthorizationService(resourceRepository)
            }

            Then("It should non be access") {
                assertNull(authService.getResourceAccess(usersResources))
            }
        }
    }
})
