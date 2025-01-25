package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    var id: Long?=null,
    var dateTime: LocalDateTime?=null,
    var status: OrderStatus?=null,
    var user: User?=null,
    var itemList: List<MenuItem?>?=null,
    var totalPrice: BigDecimal?=null
)
