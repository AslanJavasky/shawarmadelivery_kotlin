package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Node("Order")
data class OrderEntity(
    @Id
    var id: UUID? = UUID.randomUUID(),
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var status: OrderStatus = OrderStatus.NEW,
    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    var user: UserEntity=UserEntity(),
    var totalPrice: BigDecimal = BigDecimal.ZERO,
    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    var itemList: MutableList<MenuItemEntity>? = mutableListOf(),
//    @Relationship(type = "HAS_DELIVERY", direction = Relationship.Direction.OUTGOING)
//    val delivery: DeliveryEntity = DeliveryEntity()
)

fun IOrder.toOrderEntity() = OrderEntity(
    id = this.id.getUUIDFromLong(),
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = this.user!!.toUserEntity(),
    totalPrice = this.totalPrice!!,
    itemList = this.itemList?.map { it.toMenuItemEntity() }!!.toMutableList()

)

fun OrderEntity.toIOrder() = Order(
    id = this.id!!.getLongFromUUID(),
    dateTime = this.dateTime,
    status = this.status,
    user = user.toIUser(),
    itemList = itemList!!.map { it.toIMenuItem() }.toMutableList(),
    totalPrice = this.totalPrice
)


