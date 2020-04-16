package xyz.sashenka.modelauthapp.service

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.model.domain.Role
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.repository.ResourceRepository

object AuthorizationServiceSpec : Spek({
    val userResources: List<UsersResources> = listOf(
        UsersResources("A", Role.READ, "sasha"),
        UsersResources("A.AA", Role.WRITE, "sasha"),
        UsersResources("A.AA.AAA", Role.EXECUTE, "sasha"),
        UsersResources("B", Role.EXECUTE, "admin"),
        UsersResources("A.B", Role.WRITE, "admin"),
        UsersResources("A.B", Role.WRITE, "sasha"),
        UsersResources("A.B.C", Role.READ, "admin"),
        UsersResources("A.B.C", Role.WRITE, "q"),
        UsersResources("A.B", Role.EXECUTE, "q"),
        UsersResources("B", Role.READ, "q"),
        UsersResources("A.AA.AAA", Role.READ, "q"),
        UsersResources("A", Role.EXECUTE, "q"),
        UsersResources("A", Role.WRITE, "admin"),
        UsersResources("A.AA", Role.EXECUTE, "admin"),
        UsersResources("B", Role.WRITE, "sasha"),
        UsersResources("A.B", Role.EXECUTE, "sasha"),
        UsersResources("A.B.C", Role.EXECUTE, "sasha")
    )

    val resourceDaoMock = mockk<ResourceDAO>()
    every {
        resourceDaoMock.requestAccessByResource(ofType(), ofType(), ofType())
    } answers {
        val resource = userResources.find { res ->
            res.login == firstArg() &&
                res.role == thirdArg() &&
                (res.path == secondArg<List<String>>().joinToString(".") ||
                    secondArg<List<String>>().joinToString(".").startsWith(res.path)
                    )
        }
        val res = resource?.path ?: ""
        val role = resource?.role?.name ?: ""
        DBAccess(0, 0, res, role)
    }

    Feature("Authorisation service") {

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
})
