package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo

class DeliveryRepoImpl : DeliveryRepo {

    private val deliveries = mutableListOf<Delivery>()

    override fun saveDelivery(delivery: Delivery): Delivery {
        deliveries.add(delivery)
        return delivery
    }

    override fun updateDelivery(delivery: Delivery): Delivery {
        val index = deliveries.indexOfFirst { it.id == delivery.id }
        if (index != -1) deliveries[index] = delivery
        return delivery
    }

    override fun getDeliveryById(id: Long): Delivery? {
        return deliveries.find { it.id == id }
    }
}