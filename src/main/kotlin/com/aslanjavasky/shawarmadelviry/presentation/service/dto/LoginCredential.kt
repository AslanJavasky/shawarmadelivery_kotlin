package com.aslanjavasky.shawarmadelviry.presentation.service.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginCredential(
    @field:NotBlank(message = "Email required")
    var email: String? = null,
    @field:NotBlank(message = "Password required")
    @field:Size(min = 6, message = "Password should be at least 6 characters")
    var password: String? = null,
)
