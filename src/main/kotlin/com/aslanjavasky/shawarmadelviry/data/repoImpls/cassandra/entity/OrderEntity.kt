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

    @Column("date_time")
    var dateTime: LocalDateTime = LocalDateTime.now(),

    @Column
    var status: OrderStatus = OrderStatus.NEW,

    @Column("user_id")
    var userId: UUID ,

    @Column("total_price")
    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @Column("menu_items")
    var menuItemsIds: MutableList<UUID>? = mutableListOf()
)

fun IOrder.toOrderEntity() = OrderEntity(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = this.user!!.toUserEntity(),
    totalPrice = this.totalPrice!!,
    itemList = this.itemList!!.map { it.toMenuItemEntity() }.toMutableList()
)

fun OrderEntity.toIOrder() = Order(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = user.toIUser(),
    itemList = itemList!!.map { it.toIMenuItem() }.toMutableList(),
    totalPrice = this.totalPrice!!
)
