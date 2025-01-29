package com.aslanjavasky.shawarmadelviry.domain.model

import java.time.LocalDateTime

interface IDelivery {
    var id:Long?
    var address:String?
    var phone:String?
    var dateTime: LocalDateTime?
    var order:IOrder?
}