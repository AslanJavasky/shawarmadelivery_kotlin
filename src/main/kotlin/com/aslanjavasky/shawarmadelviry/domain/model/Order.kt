package com.aslanjavasky.shawarmadelviry.domain.model

import java.time.LocalDateTime

data class Order(
    var id: Long,
    var dateTime: LocalDateTime,
    var status: OrderStatus,
    var user: User,
    var itemList: MutableList<MenuItem>,
    var totalPrice: Int
)
