package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("orders")
class OrderEntity(
    @Id
    var id: Long? = null,
    var dateTime: LocalDateTime,
    var status: OrderStatus,
    var userId: Long,
    var totalPrice: BigDecimal
)

fun IOrder.toOrderEntity() = OrderEntity(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    userId = this.user!!.id!!,
    totalPrice = this.totalPrice!!
)

fun OrderEntity.toIOrder(iuser: IUser, items: MutableList<IMenuItem>) = Order(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = iuser,
    itemList = items,
    totalPrice = this.totalPrice!!
)

