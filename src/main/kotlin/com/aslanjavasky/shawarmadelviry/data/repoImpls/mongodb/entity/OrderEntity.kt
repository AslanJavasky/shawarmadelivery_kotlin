package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Document(collection = "orders")
data class OrderEntity(
    @Id
    var id: UUID? = UUID.randomUUID(),
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var status: OrderStatus = OrderStatus.NEW,
    var userId: UUID,
    var totalPrice: BigDecimal = BigDecimal.ZERO,
    var menuItemsIds: MutableList<UUID>? = mutableListOf()
)

fun IOrder.toOrderEntity() = OrderEntity(
    id = this.id.getUUIDFromLong(),
    dateTime = this.dateTime!!,
    status = this.status!!,
    userId = this.user!!.id!!.getUUIDFromLong(),
    totalPrice = this.totalPrice!!,
    menuItemsIds = this.itemList?.map { it.id.getUUIDFromLong() }?.toMutableList()

)

fun OrderEntity.toIOrder(iuser: IUser, items: MutableList<IMenuItem>) = Order(
    id = this.id!!.getLongFromUUID(),
    dateTime = this.dateTime,
    status = this.status,
    user = iuser,
    itemList = items,
    totalPrice = this.totalPrice
)


