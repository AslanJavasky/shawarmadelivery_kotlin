package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.conf.AuthUtils
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authUtils: AuthUtils
) {

    @GetMapping("/register")
    fun register(
        model: Model
    ): String {
        model.addAttribute("user", User())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute user: User): String {
        val encodedPassword = user.password?.let { authUtils.encodePassword(it) }
        val updatedUser = user.copy(password = encodedPassword)
        userService.createUser(updatedUser)
        return "redirect:/users/login"
    }

    @GetMapping("/login")
    fun showLoginForm(model: Model): String {
        model.addAttribute("email", "")
        model.addAttribute("password", "")
        return "login"
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam email: String,
        @RequestParam password: String,
        model: Model
    ): String {
        return try {
            val user = userService.getUserByEmail(email)
            if (authUtils.authenticate(password, user!!.password!!)) {
                "redirect:/menu"
            } else {
                model.addAttribute("error", "Invalid email or password")
                "login"
            }
        } catch (e: Exception) {
            model.addAttribute("error", "Login failed:${e.message}")
            return "login"
        }
    }

    @PostMapping("/delete")
    fun deleteUser(@RequestParam email: String): String {
        val user = userService.getUserByEmail(email)
        user?.let {
            userService.deleteUser(user)
        }
        return "redirect:/users/register"
    }

}