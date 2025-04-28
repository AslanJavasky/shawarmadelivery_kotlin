package com.aslanjavasky.shawarmadelviry.security.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Entity(name = "auth_users")
data class UserSecurity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val name: String="" ,

    @JvmField
    @Column(nullable = false, unique = true)
    var username: String="" ,

    @Column(nullable = false, unique = true)
    var email: String="" ,

    @JvmField
    @Column(nullable = false)
    var password: String="" ,

    var telegram: String? = null,
    var phone: String? = null,
    var address: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? =
        listOf(SimpleGrantedAuthority(this.role.name))

    override fun getPassword(): String? = this.password

    override fun getUsername(): String? = this.username
}