package com.aslanjavasky.shawarmadelviry.presentation.service.dto

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserDto(
    override var id: Long? = null,
    @field:NotBlank(message = "Name required")
    override var name: String? = null,
    @field:NotBlank(message = "Email required")
    @field:Email(message = "Email should be valid")
    override var email: String? = null,
    @field:NotBlank(message = "Password required")
    @field:Size(min = 6, message = "Password should be at least 6 characters")
    override var password: String? = null,
    @field:NotBlank(message = "Phone number required")
    @field:Pattern(regexp = "^\\+?\\d+$", message = "Only digits for phone number")
    override var phone: String? = null,
    override var telegram: String? = null,
    @field:NotBlank(message = "Address required")
    override var address: String? = null
) : IUser
