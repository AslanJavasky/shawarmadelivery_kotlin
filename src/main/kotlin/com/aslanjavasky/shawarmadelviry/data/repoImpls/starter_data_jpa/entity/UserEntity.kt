package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

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
    var orders: List<OrderEntity> = emptyList(),
) : BaseEntity()

fun IUser.toUserEntity() = UserEntity(
    id = this.id,
    name = this.name!!,
    email = this.email!!,
    password = this.password!!,
    phone = this.phone,
    telegram = this.telegram,
    address = this.address,
)

fun UserEntity.toIUser() = User(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    phone = this.phone,
    telegram = this.telegram,
    address = this.address,
)