package com.aslanjavasky.shawarmadelviry.security.repo

import com.aslanjavasky.shawarmadelviry.security.entity.UserSecurity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserSecurityRepository : JpaRepository<UserSecurity, Long> {
    fun findByUsername(username: String): UserSecurity?
    fun findByEmail(email: String): UserSecurity?
}