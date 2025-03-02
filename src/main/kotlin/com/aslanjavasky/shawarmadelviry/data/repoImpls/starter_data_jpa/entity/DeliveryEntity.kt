package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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
)
