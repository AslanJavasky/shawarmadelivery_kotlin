package com.aslanjavasky.shawarmadelviry.presentation.service.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class OrderDto(
    @field:NotBlank(message = "Name required")
    var username: String? = null,
    @field:NotBlank(message = "Address required")
    var address: String? = null,
    @field:NotBlank(message = "Phone number required")
    @field:Pattern(regexp = "^\\+?\\d+$", message = "Only digits for phone number")
    var phone: String? = null
)
