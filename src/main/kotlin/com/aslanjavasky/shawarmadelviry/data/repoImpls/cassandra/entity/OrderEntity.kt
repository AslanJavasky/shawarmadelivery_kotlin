package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("orders")
data class OrderEntity(
    @PrimaryKey
    var id: UUID? = UUID.randomUUID(),

//    @Column
    var dateTime: LocalDateTime = LocalDateTime.now(),

//    @Column
    var status: OrderStatus = OrderStatus.NEW,

//    @Column
    var userId: UUID,

//    @Column
    var totalPrice: BigDecimal = BigDecimal.ZERO,

//    @Column
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


