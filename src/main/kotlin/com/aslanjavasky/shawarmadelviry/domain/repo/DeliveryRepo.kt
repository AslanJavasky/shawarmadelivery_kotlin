package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery

interface DeliveryRepo {
    fun saveDelivery(delivery: Delivery): Delivery
    fun updateDelivery(delivery: Delivery): Delivery
    fun getDeliveryById(id: Long): Delivery?
}