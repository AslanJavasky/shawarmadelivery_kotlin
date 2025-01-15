package com.aslanjavasky.shawarmadelviry.domain.model

import java.time.LocalDateTime

data class Delivery(
    var id:Long,
    var address:String,
    var dateTime: LocalDateTime,
    var order:Order
)
