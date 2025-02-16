package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class UserEntity(
    @Id
    override var id: Long? = null,
    override var name: String?,
    override var email: String?,
    override var password: String?,
    override var phone: String?,
    override var telegram: String?,
    override var address: String?
) : IUser


fun IUser.toUserEntity() = UserEntity(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
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
