package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity

import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@Node("User")
data class UserEntity(
    @Id
    var id: UUID? = UUID.randomUUID(),
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var telegram: String? = null,
    var phone: String? = null,
    var address: String? = null,
    @Relationship(type = "PLACED_ORDER", direction = Relationship.Direction.OUTGOING)
    val orders: List<OrderEntity> = emptyList()
)

//fun IUser.toUserEntity() = UserEntity(
//    id = this.id.getUUIDFromLong(),
//    name = this.name!!,
//    email = this.email!!,
//    password = this.password!!,
//    phone = this.phone,
//    telegram = this.telegram,
//    address = this.address,
//)
//
//fun UserEntity.toIUser() = User(
//    id = this.id!!.mostSignificantBits,
//    name = this.name,
//    email = this.email,
//    password = this.password,
//    phone = this.phone,
//    telegram = this.telegram,
//    address = this.address,
//)
//
//fun Long?.getUUIDFromLong(): UUID {
//    return if (this == null) {
//        UUID(Random().nextLong(), 0)
//    }else {
//        UUID(this, 0)
//    }
//}
//
//fun UUID.getLongFromUUID(): Long {
//    return this.mostSignificantBits
//}