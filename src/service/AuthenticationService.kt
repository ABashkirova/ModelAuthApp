package service

import model.User

class AuthenticationService {
    private val users = listOf(
        User(login = "sasha", password = "123"),
        User(login = "admin", password = "qwerty"),
        User(login = "q", password = "@#\$%^&*!"),
        User(login = "aleksandra", password = "abc")
    )

}