package com.aslanjavasky.shawarmadelviry.security.controller


import com.aslanjavasky.shawarmadelviry.security.entity.LoginCredential
import com.aslanjavasky.shawarmadelviry.security.entity.UserSecurity
import com.aslanjavasky.shawarmadelviry.security.service.UserSecurityService
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/auth")
class AuthController(
    private val userService: UserSecurityService,
) {

    @GetMapping("/register")
    fun register(
        model: Model
    ): String {
        model.addAttribute("user",  UserSecurity())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(
        @Valid @ModelAttribute("user") user: UserSecurity,
        result: BindingResult,
        model: Model
    ): String {
        if (result.hasErrors()) {
            model.addAttribute("user", user)
            return "register"
        }
        userService.registerUser(user)
        return "redirect:/auth/login"
    }

    @GetMapping("/login")
    fun showLoginForm(model: Model): String {
        model.addAttribute("credential", LoginCredential())
        return "login"
    }

//    @PostMapping("/login")
//    fun loginUser(
//        @Valid @ModelAttribute("credential") credential: LoginCredential,
//        result:BindingResult,
//        model: Model
//    ): String {
//
//        if (result.hasErrors()){
//            model.addAttribute("credential",credential)
//            return "login"
//        }
//
//        return try {
//            val user = userService.getUserByEmail(credential.email!!)
//            if (authUtils.authenticate(credential.password!!, user!!.password!!)) {
//                if (sessionInfoService.username.isNullOrBlank()){
//                    val userDto = UserDto()
//                    userDto.name = user.name
//                    userDto.phone = user.phone
//                    userDto.address = user.address
//                    userDto.email = user.email
//                    userDto.telegram = user.telegram
//                    userDto.password = user.password
//                    sessionInfoService.setUserFields(userDto)
//                }
//                "redirect:/menu"
//            } else {
//                model.addAttribute("error", "Invalid email or password")
//                "login"
//            }
//        } catch (e: Exception) {
//            model.addAttribute("error", "Login failed:${e.message}")
//            return "login"
//        }
//    }



}