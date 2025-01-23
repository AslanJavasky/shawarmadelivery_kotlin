package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/register")
    fun registy(
        @RequestParam name: String,
        model: Model
    ): String {
        model.addAttribute("name", name)
        return "register"
    }


}