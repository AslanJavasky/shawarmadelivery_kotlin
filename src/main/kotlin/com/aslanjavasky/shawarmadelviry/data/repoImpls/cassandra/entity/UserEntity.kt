package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("users")
data class UserEntity(

    @PrimaryKey
    var id: UUID? = UUID.randomUUID(),

    @Column
    var name: String = "",

    @Column
    var email: String = "",

    @Column
    var password: String = "",

    @Column
    var telegram: String? = null,

    @Column
    var phone: String? = null,

    @Column
    var address: String? = null,

    @Column("order_ids")
    var orders: List<UUID> = emptyList(),
)

fun IUser.toUserEntity() = UserEntity(
    id = if (this.id==null) UUID.randomUUID() else this.id!!.getUUIDFromLong() ,
    name = this.name!!,
    email = this.email!!,
    password = this.password!!,
    phone = this.phone,
    telegram = this.telegram,
    address = this.address,
)

fun UserEntity.toIUser() = User(
    id = this.id!!.mostSignificantBits,
    name = this.name,
    email = this.email,
    password = this.password,
    phone = this.phone,
    telegram = this.telegram,
    address = this.address,
)

fun Long.getUUIDFromLong(): UUID {
    val mostSignBit: Long = this
    val leastSignBit: Long = (this shl 32) or (this ushr 32)
    return UUID(mostSignBit, leastSignBit)
}