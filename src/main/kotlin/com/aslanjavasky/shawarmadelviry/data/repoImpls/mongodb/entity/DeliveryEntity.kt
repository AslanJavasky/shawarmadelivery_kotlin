package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "deliveries")
data class DeliveryEntity(
    @Id
    var id: UUID? = UUID.randomUUID(),
    var address: String = "",
    var phone: String = "",
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var orderId: UUID
)

fun IDelivery.toDeliveryEntity() = DeliveryEntity(
    id = this.id.getUUIDFromLong(),
    address = this.address!!,
    phone = this.phone!!,
    dateTime = this.dateTime!!,
    orderId = this.order!!.id.getUUIDFromLong()
)

fun DeliveryEntity.toIDelivery(order: IOrder) = Delivery(
    id = this.id!!.getLongFromUUID(),
    address = this.address,
    phone = this.phone,
    dateTime = this.dateTime,
    order = order
)
