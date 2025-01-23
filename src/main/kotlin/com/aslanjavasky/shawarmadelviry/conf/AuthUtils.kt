package com.aslanjavasky.shawarmadelviry.conf

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthUtils(
    private val passwordEncoder: PasswordEncoder
) {
    fun encodePassword(rawPassword: String) = passwordEncoder.encode(rawPassword)

    fun authenticate(rawPassword: String, encodedPassword: String) =
        passwordEncoder.matches(rawPassword, encodedPassword)
}