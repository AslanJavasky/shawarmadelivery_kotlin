//package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity
//
//import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
//import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
//import org.springframework.data.annotation.Id
//import org.springframework.data.redis.core.RedisHash
//import org.springframework.data.redis.core.index.Indexed
//import java.io.Serializable
//import java.time.LocalDateTime
//import java.util.*
//
//@RedisHash("deliveries")
//data class DeliveryEntity(
//    @Id
//    var id: UUID? = UUID.randomUUID(),
//    var address: String = "",
//    var phone: String = "",
//    var dateTime: LocalDateTime = LocalDateTime.now(),
//    @Indexed
//    var orderId: UUID
//) : Serializable
//
//fun IDelivery.toDeliveryEntity() = DeliveryEntity(
//    id = this.id.getUUIDFromLong(),
//    address = this.address!!,
//    phone = this.phone!!,
//    dateTime = this.dateTime!!,
//    orderId = this.order!!.id.getUUIDFromLong()
//)
//
//fun DeliveryEntity.toIDelivery(order: IOrder) = Delivery(
//    id = this.id!!.getLongFromUUID(),
//    address = this.address,
//    phone = this.phone,
//    dateTime = this.dateTime,
//    order = order
//)
