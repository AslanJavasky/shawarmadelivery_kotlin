package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo

open class DeliveryInterractor(
    private val repo: DeliveryRepo
) {
    fun createDelivery(delivery: IDelivery) = repo.saveDelivery(delivery)
    fun changeDelivery(delivery: IDelivery) = repo.updateDelivery(delivery)
    fun getDeliveryById(id: Long) = repo.getDeliveryById(id)
}