package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

interface IOrder {
    var id: Long?
    var dateTime: LocalDateTime?
    var status: OrderStatus?
    var user: IUser?
    var itemList: MutableList<IMenuItem>?
    var totalPrice: BigDecimal?
}