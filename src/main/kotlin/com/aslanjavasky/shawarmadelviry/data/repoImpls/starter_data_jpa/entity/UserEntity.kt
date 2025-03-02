package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    var telegram: String? = null,
    var phone: String? = null,
    var address: String? = null,

    @OneToMany(mappedBy = "user")
    var orders:List<OrderEntity> = emptyList()
)