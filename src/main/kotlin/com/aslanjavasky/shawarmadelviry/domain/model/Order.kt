package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    override var id: Long? = null,
    override var dateTime: LocalDateTime? = null,
    override var status: OrderStatus? = null,
    override var user: IUser? = null,
    override var itemList: List<IMenuItem?>? = null,
    override var totalPrice: BigDecimal? = null
) : IOrder
