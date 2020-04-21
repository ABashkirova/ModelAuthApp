package xyz.sashenka.modelauthapp.service

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ValidatingServiceSpec : Spek({
    val validationService = ValidatingService()

    Feature("Validation") {
        Scenario("login is valid") {

            Then("it should be valid of sasha") {
                assertTrue(validationService.isLoginValid("sasha"))
            }

            Then("it should be valid of 1 symbol") {
                assertTrue(validationService.isLoginValid("s"))
            }

            Then("it should be valid of 10 symbols") {
                assertTrue(validationService.isLoginValid("bashkirova"))
            }

            Then("it should be not valid of string with numbers") {
                assertFalse(validationService.isLoginValid("sasha123"))
            }

            Then("it should be valid of empty string") {
                assertFalse(validationService.isLoginValid(""))
            }

            Then("it should be valid of >10 sumbol") {
                assertFalse(validationService.isLoginValid("abashkirova"))
            }

            Then("it should be valid of CAPS") {
                assertFalse(validationService.isLoginValid("SASHA"))
            }

            Then("it should be valid of null") {
                assertFalse(validationService.isLoginValid(null))
            }
        }

        Scenario("role is valid") {
            Then("it should be valid of READ") {
                assertTrue(validationService.isRoleValid("READ"))
            }

            Then("it should be valid of WRITE") {
                assertTrue(validationService.isRoleValid("WRITE"))
            }

            Then("it should be valid of EXECUTE") {
                assertTrue(validationService.isRoleValid("EXECUTE"))
            }

            Then("it should be valid of not R-W-E role") {
                assertFalse(validationService.isRoleValid("DELETE"))
            }

            Then("it should be valid of not caps symbols") {
                assertFalse(validationService.isRoleValid("write"))
            }

            Then("it should be valid of null") {
                assertFalse(validationService.isRoleValid(null))
            }

            Then("it should be valid of empty string") {
                assertFalse(validationService.isRoleValid(""))
            }
        }

        Scenario("date can be parsed") {
            Then("it should be parsed of yyyy-MM-dd") {
                assertNotNull(validationService.parseDate(("2000-04-01")))
            }

            Then("it should be not parsed of 01-2000-04") {
                assertNull(validationService.parseDate("01-2000-04"))
            }

            Then("it should be not parsed of empty string") {
                assertNull(validationService.parseDate(""))
            }

            Then("it should be not parsed of error date 2000-04-00") {
                assertNull(validationService.parseDate("2000-04-00"))
            }

            Then("it should be not parsed of error date 2000-13-01") {
                assertNull(validationService.parseDate("2000-13-01"))
            }
        }

        Scenario("volume can be parsed") {
            Then("it should be parsed of 10") {
                assertNotNull(validationService.parseVolume(("10")))
                assertEquals(10, validationService.parseVolume(("10")))
            }

            Then("it should be not parsed of 10.1") {
                assertNull(validationService.parseVolume("10.1"))
            }

            Then("it should be not parsed of 10,1") {
                assertNull(validationService.parseVolume("10,1"))
            }

            Then("it should be parsed of empty string") {
                assertNull(validationService.parseVolume(""))
            }
        }

        Scenario("volume is valid") {
            Then("it should be valid of 10") {
                assertTrue(validationService.isVolumeValid(10))
            }

            Then("it should be valid of 1") {
                assertTrue(validationService.isVolumeValid(1))
            }

            Then("it should be valid of MAX_VALUE") {
                assertTrue(validationService.isVolumeValid(Int.MAX_VALUE))
            }

            Then("it should be not valid of null") {
                assertFalse(validationService.isVolumeValid(null))
            }

            Then("it should be not valid of 0") {
                assertFalse(validationService.isVolumeValid(0))
            }
        }

        Scenario("date are valid") {
            Then("they should be valid of 2000-04-01 < 2000-04-02") {
                assertTrue(
                    validationService.areDatesValid(
                        validationService.parseDate("2000-04-01"),
                        validationService.parseDate("2000-04-02")
                    )
                )
            }

            Then("they should be not valid of 2000-04-02 > 2000-04-01") {
                assertFalse(
                    validationService.areDatesValid(
                        validationService.parseDate("2000-04-02"),
                        validationService.parseDate("2000-04-01")
                    )
                )
            }

            Then("they should be not valid of first null") {
                assertFalse(
                    validationService.areDatesValid(
                        validationService.parseDate(""),
                        validationService.parseDate("2000-04-01")
                    )
                )
            }

            Then("they should be not valid of second null") {
                assertFalse(
                    validationService.areDatesValid(
                        validationService.parseDate("2000-04-01"),
                        validationService.parseDate("")
                    )
                )
            }

            Then("they should be not valid of nulls") {
                assertFalse(validationService.areDatesValid(null, null))
            }
        }
    }
})
