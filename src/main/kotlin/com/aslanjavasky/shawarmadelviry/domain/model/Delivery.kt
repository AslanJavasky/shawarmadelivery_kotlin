package com.aslanjavasky.shawarmadelviry.domain.model

import java.time.LocalDateTime

data class Delivery(
    var id:Long?=null,
    var address:String?=null,
    var phone:String?=null,
    var dateTime: LocalDateTime?=null,
    var order:Order?=null
)
