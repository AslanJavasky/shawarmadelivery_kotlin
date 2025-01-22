package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller

@Controller
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Lazy
class UserController(
    private val userService: UserService
) {
    init {
        println("UserController bean is created!")
    }
    fun createUser(user: User) = userService.createUser(user)

    fun deleteUser(user: User) = userService.deleteUser(user)

}