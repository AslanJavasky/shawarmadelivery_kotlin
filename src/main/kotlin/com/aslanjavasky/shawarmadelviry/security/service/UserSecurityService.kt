package com.aslanjavasky.shawarmadelviry.security.service

import com.aslanjavasky.shawarmadelviry.security.entity.UserSecurity
import com.aslanjavasky.shawarmadelviry.security.repo.UserSecurityRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSecurityService(
    private val repo: UserSecurityRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        return repo.findByUsername(username!!)
            ?: throw UsernameNotFoundException("User not found")
    }

    fun registerUser(user: UserSecurity): UserSecurity{
        val encodedPassword=passwordEncoder.encode(user.password)
        user.password=encodedPassword
        user.username=user.email

        return repo.save(user)
    }


}