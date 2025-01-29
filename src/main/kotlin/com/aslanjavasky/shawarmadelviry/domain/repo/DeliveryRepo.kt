package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery

interface DeliveryRepo {
    fun saveDelivery(delivery: IDelivery): IDelivery
    fun updateDelivery(delivery: IDelivery): IDelivery
    fun getDeliveryById(id: Long): IDelivery?
}