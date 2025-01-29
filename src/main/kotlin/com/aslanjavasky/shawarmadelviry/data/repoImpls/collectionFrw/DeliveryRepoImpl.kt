package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class DeliveryRepoImpl : DeliveryRepo {

    private val deliveries = mutableListOf<IDelivery>()
    private var nextId = AtomicLong(1)

    override fun saveDelivery(delivery: IDelivery): IDelivery {
        delivery.id=nextId.getAndIncrement()
        deliveries.add(delivery)
        return delivery
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        val index = deliveries.indexOfFirst { it.id == delivery.id }
        if (index != -1) deliveries[index] = delivery
        return delivery
    }

    override fun getDeliveryById(id: Long): IDelivery? {
        return deliveries.find { it.id == id }
    }
}