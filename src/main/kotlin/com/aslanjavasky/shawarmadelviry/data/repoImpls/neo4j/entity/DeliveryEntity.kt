//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity
//
//import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
//import org.springframework.data.annotation.Id
//import org.springframework.data.neo4j.core.schema.Node
//import org.springframework.data.neo4j.core.schema.Relationship
//import java.time.LocalDateTime
//import java.util.*
//
//@Node("Delivery")
//data class DeliveryEntity(
//    @Id
//    var id: UUID? = UUID.randomUUID(),
//    var address: String = "",
//    var phone: String = "",
//    var dateTime: LocalDateTime = LocalDateTime.now(),
//    @Relationship(type = "FOR_ORDER", direction = Relationship.Direction.OUTGOING )
//    var order: OrderEntity = OrderEntity()
//)
//
//fun IDelivery.toDeliveryEntity() = DeliveryEntity(
//    id = this.id.getUUIDFromLong(),
//    address = this.address!!,
//    phone = this.phone!!,
//    dateTime = this.dateTime!!,
//    order= this.order!!.toOrderEntity()
//)
//
//fun DeliveryEntity.toIDelivery() = Delivery(
//    id = this.id!!.getLongFromUUID(),
//    address = this.address,
//    phone = this.phone,
//    dateTime = this.dateTime,
//    order = order.toIOrder()
//)
