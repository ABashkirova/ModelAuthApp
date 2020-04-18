package xyz.sashenka.modelauthapp

import org.junit.Assert.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import xyz.sashenka.modelauthapp.di.Container
import xyz.sashenka.modelauthapp.model.ExitCode

object ApplicationSpec : Spek({
    lateinit var application: Application
    describe("Help") {
        mapOf(
            arrayOf("") to ExitCode.HELP,
            // Выход из программы за счет внешней библиотеки
            // arrayOf("-h") to ExitCode.SUCCESS,
            arrayOf("-bla") to ExitCode.HELP
        ).forEach { (input, expected) ->
            describe("Print help for ${input.joinToString()}") {
                it("Correctly returns $expected") {
                    application = Application(input, Container())
                    assertEquals(expected, application.run())
                }
            }
        }
    }

    describe("Authentication") {
        mapOf(
            ("-login sasha -pass 123".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-pass 123 -login sasha".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login SASHA -pass 123".split(" ")).toTypedArray() to ExitCode.INVALID_LOGIN_FORMAT,
            ("-login SA12 -pass 123".split(" ")).toTypedArray() to ExitCode.INVALID_LOGIN_FORMAT,
            ("-login   -pass pass".split(" ")).toTypedArray() to ExitCode.HELP,
            ("-login abcdqwertyqwerty -pass pass".split(" ")).toTypedArray() to ExitCode.INVALID_LOGIN_FORMAT,
            ("-login vasya -pass 123".split(" ")).toTypedArray() to ExitCode.UNKNOWN_LOGIN,
            ("-login admin -pass 1234".split(" ")).toTypedArray() to ExitCode.WRONG_PASSWORD,
            ("-login admin -pass".split(" ")).toTypedArray() to ExitCode.HELP,
            ("-login admin -pass qwerty".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*!".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login abcdefghij -pass abc".split(" ").toTypedArray()) to ExitCode.SUCCESS
        ).forEach { (input, expected) ->
            describe("Check authentication for ${input.joinToString()}") {
                it("Correctly returns $expected") {
                    application = Application(input, Container())
                    assertEquals(expected, application.run())
                }
            }
        }
    }

    describe("Authorization") {
        mapOf(
            ("-login sasha -pass 123 -role READ -res A".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login sasha -pass 123 -role DELETE -res A".split(" ")).toTypedArray() to ExitCode.UNKNOWN_ROLE,
            ("-login sasha -pass 123 -role WRITE -res A".split(" ")).toTypedArray() to ExitCode.NO_ACCESS,
            ("-login sasha -pass 123 -role WRITE -res a.b.c".split(" ")).toTypedArray() to ExitCode.NO_ACCESS,
            ("-login sasha -pass 123 -role READ -res A.B".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login sasha -pass 123 -role READ -res A.B.C.D".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login admin -pass qwerty -role EXECUTE -res A.AA".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login admin -pass qwerty -role EXECUTE -res A.B".split(" ")).toTypedArray() to ExitCode.NO_ACCESS,
            ("-login q -pass @#$%^&*! -role READ".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass 1234 -role DELETE -res A.B".split(" ")).toTypedArray() to ExitCode.WRONG_PASSWORD,
            ("-login q -pass @#$%^&*! -role READ -res A.AA.AAA".split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*! -role READ -res A.AA".split(" ").toTypedArray()) to ExitCode.NO_ACCESS,
            ("-role READ -res A -login sasha -pass 123".split(" ").toTypedArray()) to ExitCode.SUCCESS,
            ("-login sasha -pass 123 -role Write -res A".split(" ").toTypedArray()) to ExitCode.UNKNOWN_ROLE,
            ("-login sasha -pass 123 -role write -res A".split(" ").toTypedArray()) to ExitCode.UNKNOWN_ROLE
        ).forEach { (input, expected) ->
            describe("Check Authorization for ${input.joinToString()}") {
                it("Correctly returns $expected") {
                    application = Application(input, Container())
                    assertEquals(expected, application.run())
                }
            }
        }
    }

    describe("Accounting") {
        mapOf(
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol ten"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol -1"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-00 -de 2000-02-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-32 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-02-15 -de 2000-01-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login sasha -pass 123 -role READ -res A -ds 2120-02-15 -de 2120-01-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.INVALID_ACTIVITY,
            ("-login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15 -vol 20"
                .split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login admin -pass qwerty -role EXECUTE -res A.AA -ds 2000-01-15 -de 2000-02-15 -vol 100"
                .split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*! -role WRITE -res A.B.C -de 2000-02-15 -vol 10"
                .split(" ")).toTypedArray() to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15"
                .split(" ").toTypedArray()) to ExitCode.SUCCESS,
            ("-login q -pass @#$%^&*! -role DELETE -res A.B.C -ds 2000-01-15 -de 2000-02-15"
                .split(" ").toTypedArray()) to ExitCode.UNKNOWN_ROLE,
            ("-login q -pass !@#$% -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15"
                .split(" ").toTypedArray()) to ExitCode.WRONG_PASSWORD,
            ("-res A.B.C -ds 2000-01-15 -vol 10 -login q -pass @#$%^&*! -role WRITE"
                .split(" ").toTypedArray()) to ExitCode.SUCCESS,
            ("-login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol 10.6"
                .split(" ").toTypedArray()) to ExitCode.INVALID_ACTIVITY
        ).forEach { (input, expected) ->
            describe("Check Accounting for ${input.joinToString()}") {
                it("Correctly returns $expected") {
                    application = Application(input, Container())
                    assertEquals(expected, application.run())
                }
            }
        }
    }
})
