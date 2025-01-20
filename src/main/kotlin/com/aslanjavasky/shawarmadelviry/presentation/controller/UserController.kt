package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import org.springframework.stereotype.Controller

@Controller
class UserController(
    private val userService: UserService
) {
    fun createUser(user: User) = userService.createUser(user)

    fun deleteUser(user: User) = userService.deleteUser(user)

}