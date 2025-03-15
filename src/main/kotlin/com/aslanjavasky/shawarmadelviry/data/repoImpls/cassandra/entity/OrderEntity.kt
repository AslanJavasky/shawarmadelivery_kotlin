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
    id = if(this.id == null) UUID.randomUUID() else this.id!!.getUUIDFromLong(),
    dateTime = this.dateTime!!,
    status = this.status!!,
    userId = this.user!!.id!!.getUUIDFromLong(),
    totalPrice = this.totalPrice!!
)

fun OrderEntity.toIOrder(iuser: IUser, items: MutableList<IMenuItem>) = Order(
    id = this.id!!.mostSignificantBits,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = iuser,
    itemList = items,
    totalPrice = this.totalPrice!!
)


