package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo

open class DeliveryInterractor(
    private val repo: DeliveryRepo
) {
    fun createDelivery(delivery: Delivery) = repo.saveDelivery(delivery)
    fun changeDelivery(delivery: Delivery) = repo.updateDelivery(delivery)
    fun getDeliveryById(id: Long) = repo.getDeliveryById(id)
}