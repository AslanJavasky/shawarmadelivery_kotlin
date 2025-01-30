package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.conf.AuthUtils
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.LoginCredential
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@Controller
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authUtils: AuthUtils,
    private val sessionInfoService: SessionInfoService
) {

    @GetMapping("/register")
    fun register(
        model: Model
    ): String {
        model.addAttribute("userDto", UserDto())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(
        @Valid @ModelAttribute("userDto") userDto: UserDto,
        result: BindingResult,
        model: Model
    ): String {
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto)
            return "register"
        }
        val encodedPassword = userDto.password?.let { authUtils.encodePassword(it) }
        val updatedUser = userDto.copy(password = encodedPassword)
        userService.createUser(updatedUser)
        sessionInfoService.setUserFields(userDto)
        return "redirect:/users/login"
    }

    @GetMapping("/login")
    fun showLoginForm(model: Model): String {
        model.addAttribute("credential", LoginCredential())
        return "login"
    }

    @PostMapping("/login")
    fun loginUser(
        @Valid @ModelAttribute("credential") credential: LoginCredential,
        result:BindingResult,
        model: Model
    ): String {

        if (result.hasErrors()){
            model.addAttribute("credential",credential)
            return "login"
        }

        return try {
            val user = userService.getUserByEmail(credential.email!!)
            if (authUtils.authenticate(credential.password!!, user!!.password!!)) {
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