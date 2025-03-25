package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.getLongFromUUID
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.getUUIDFromLong
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.toDeliveryEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.toIDelivery
import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.DeliveryMongoRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("DeliveryRepoAdapter_MongoDB")
class DeliveryRepoAdapter(
    private val deliveryRepository: DeliveryMongoRepository,
    @Qualifier("OrderRepoAdapter_MongoDB") private val orderRepoAdapter: OrderRepoAdapter,
) : DeliveryRepo {


    override fun saveDelivery(delivery: IDelivery): IDelivery {
        val savedDelivery=deliveryRepository.save(delivery.toDeliveryEntity())
        return getDeliveryById(savedDelivery.id!!.getLongFromUUID())
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        return saveDelivery(delivery)
    }

    override fun getDeliveryById(id: Long): IDelivery {
        val deliveryEntity = deliveryRepository.findById(id.getUUIDFromLong())
            .orElseThrow { RuntimeException("Delivery not found with id: $id") }
        return deliveryEntity.toIDelivery(orderRepoAdapter.getIOrderById(deliveryEntity.orderId))
    }
}