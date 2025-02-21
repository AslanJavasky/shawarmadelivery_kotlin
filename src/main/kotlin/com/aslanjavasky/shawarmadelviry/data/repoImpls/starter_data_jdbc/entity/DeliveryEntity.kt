package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("deliveries")
class DeliveryEntity (
    @Id
    var id:Long? = null,
    var address:String?,
    var phone:String?,
    var dateTime: LocalDateTime?,
    var orderId: Long?
)

fun IDelivery.toDeliveryEntity() = DeliveryEntity(
    id = this.id,
    address = this.address,
    phone = this.phone,
    dateTime = this.dateTime,
    orderId = this.order!!.id!!
)

fun DeliveryEntity.toIDelivery(order: IOrder) = Delivery(
    id = this.id,
    address = this.address,
    phone = this.phone,
    dateTime = this.dateTime,
    order = order
)