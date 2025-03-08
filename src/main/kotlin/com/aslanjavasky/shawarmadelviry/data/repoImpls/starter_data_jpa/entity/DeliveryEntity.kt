package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.DeliveryEntity
import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@Entity(name = "deliveries")
data class DeliveryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var address: String = "",
    @Column(nullable = false)
    var phone: String = "",
    @Column(nullable = false, name = "date_time")
    var dateTime: LocalDateTime = LocalDateTime.now(),

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity = OrderEntity()
) : BaseEntity()

fun IDelivery.toDeliveryEntity() = DeliveryEntity(
    id = this.id,
    address = this.address!!,
    phone = this.phone!!,
    dateTime = this.dateTime!!,
    order = this.order!!.toOrderEntity()
)

fun DeliveryEntity.toIDelivery() = Delivery(
    id = this.id,
    address = this.address,
    phone = this.phone,
    dateTime = this.dateTime,
    order = this.order.toIOrder()
)
