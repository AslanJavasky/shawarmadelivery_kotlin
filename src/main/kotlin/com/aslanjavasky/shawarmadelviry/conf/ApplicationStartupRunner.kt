package com.aslanjavasky.shawarmadelviry.conf

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.controller.UserController
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ApplicationStartupRunner(
    private val userController: UserController
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        userController.createUser(User())
        userController.deleteUser(User())
    }
}