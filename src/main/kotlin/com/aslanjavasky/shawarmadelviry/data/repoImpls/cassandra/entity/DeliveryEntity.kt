//package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity
//
//import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
//import org.springframework.data.cassandra.core.mapping.Column
//import org.springframework.data.cassandra.core.mapping.PrimaryKey
//import org.springframework.data.cassandra.core.mapping.Table
//import java.time.LocalDateTime
//import java.util.*
//
//
//@Table("deliveries")
//data class DeliveryEntity(
//    @PrimaryKey
//    var id: UUID? = UUID.randomUUID(),
//
//    @Column
//    var address: String = "",
//    @Column
//    var phone: String = "",
//    @Column("date_time")
//    var dateTime: LocalDateTime = LocalDateTime.now(),
//
//    @Column("order_id")
//    var orderId: UUID
//)
//
////fun IDelivery.toDeliveryEntity() = DeliveryEntity(
////    id = this.id,
////    address = this.address!!,
////    phone = this.phone!!,
////    dateTime = this.dateTime!!,
////    order = this.order!!.toOrderEntity()
////)
////
////fun DeliveryEntity.toIDelivery() = Delivery(
////    id = this.id,
////    address = this.address,
////    phone = this.phone,
////    dateTime = this.dateTime,
////    order = this.order.toIOrder()
////)
