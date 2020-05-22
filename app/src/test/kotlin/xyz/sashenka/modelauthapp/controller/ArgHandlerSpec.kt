package xyz.sashenka.modelauthapp.controller

import org.spekframework.spek2.Spek
import org.spekframework.spek2.meta.Ignore
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ArgHandlerSpec : Spek({
    Feature("ArgHandler") {
        Scenario("getting authentication data") {
            val argHandler = ArgHandler()
            argHandler.parse(
                arrayOf(
                    "-login", "sasha", "-pass", "123"
                )
            )

            Then("it should have a login of sasha") {
                assertEquals("sasha", argHandler.login)
            }

            Then("it should have a password of 123") {
                assertEquals("123", argHandler.password)
            }

            Then("it should be not null authentication data") {
                assertNotNull(argHandler.getAuthenticationData())
            }

            Then("it should be null authorization data") {
                assertNull(argHandler.getAuthorizationData())
            }

            Then("it should be null accouting data") {
                assertNull(argHandler.getAccountingData())
            }
        }

        Scenario("getting authorization data") {
            val argHandler = ArgHandler()
            argHandler.parse(
                arrayOf(
                    "-login", "sasha", "-pass", "123",
                    "-role", "READ", "-res", "A"
                )
            )
            println(argHandler.role)
            Then("it should have a role of read") {
                assertEquals("READ", argHandler.role)
            }

            Then("it should have a resource of A") {
                assertEquals("A", argHandler.resource)
            }

            Then("it should be not null authentication data") {
                assertNotNull(argHandler.getAuthenticationData())
            }

            Then("it should be not null authorization data") {
                assertNotNull(argHandler.getAuthorizationData())
            }

            Then("it should be null accouting data") {
                assertNull(argHandler.getAccountingData())
            }
        }

        Scenario("getting accounting data") {
            val argHandler = ArgHandler()
            argHandler.parse(
                arrayOf(
                    "-login", "sasha", "-pass", "123",
                    "-role", "READ", "-res", "A",
                    "-ds", "2000-01-15", "-de", "2000-02-15", "-vol", "10"
                )
            )

            Then("it should have a ds of 2000-01-15") {
                assertEquals("2000-01-15", argHandler.dateStart)
            }

            Then("it should have a resource of A") {
                assertEquals("2000-02-15", argHandler.dateEnd)
            }

            Then("it should have a volume") {
                assertEquals("10", argHandler.volume)
            }

            Then("it should be not null authentication data") {
                assertNotNull(argHandler.getAuthenticationData())
            }

            Then("it should be not null authorization data") {
                assertNotNull(argHandler.getAuthorizationData())
            }

            Then("it should be not null accouting data") {
                assertNotNull(argHandler.getAccountingData())
            }
        }


        Scenario("getting null authentication data") {
            val argHandler = ArgHandler()
            argHandler.parse(emptyArray())

            Then("it should have login") {
                assertNull(argHandler.login)
            }

            Then("it should have password") {
                assertNull(argHandler.password)
            }

            Then("it should be not null authentication data") {
                assertNull(argHandler.getAuthenticationData())
            }

            Then("it should be not null authorization data") {
                assertNull(argHandler.getAuthorizationData())
            }

            Then("it should be not null accounting data") {
                assertNull(argHandler.getAccountingData())
            }
        }
    }
})
