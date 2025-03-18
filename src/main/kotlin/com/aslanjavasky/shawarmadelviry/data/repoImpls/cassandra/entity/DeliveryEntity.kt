package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime
import java.util.*


@Table("deliveries")
data class DeliveryEntity(
    @PrimaryKey
    var id: UUID? = UUID.randomUUID(),

//    @Column
    var address: String = "",
//    @Column
    var phone: String = "",
//    @Column
    var dateTime: LocalDateTime = LocalDateTime.now(),

//    @Column
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
