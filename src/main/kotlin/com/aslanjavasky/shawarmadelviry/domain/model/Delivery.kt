package com.aslanjavasky.shawarmadelviry.domain.model

import java.time.LocalDateTime

data class Delivery(
    override var id: Long? = null,
    override var address: String? = null,
    override var phone: String? = null,
    override var dateTime: LocalDateTime? = null,
    override var order: IOrder? = null
) : IDelivery
