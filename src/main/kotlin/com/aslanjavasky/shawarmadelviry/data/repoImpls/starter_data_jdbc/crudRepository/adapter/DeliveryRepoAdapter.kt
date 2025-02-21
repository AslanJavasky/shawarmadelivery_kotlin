package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.DeliveryRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toDeliveryEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("DeliveryRepoAdapter_CRUD")
class DeliveryRepoAdapter(
    @Qualifier("DeliveryRepoExtCrudRepo") private val deliveryRepository: DeliveryRepository,
    @Qualifier("OrderRepoAdapter_CRUD") private val orderRepoAdapter: OrderRepoAdapter,
) : DeliveryRepo {
    override fun saveDelivery(delivery: IDelivery): IDelivery {
        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery(delivery.order!!)
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery(delivery.order!!)
    }

    override fun getDeliveryById(id: Long): IDelivery? {
        val deliveryEntity = deliveryRepository.findById(id)
            .orElseThrow { RuntimeException("Delivery not found with id: $id") }
        return deliveryEntity.toIDelivery(orderRepoAdapter.getIOrderById(deliveryEntity.orderId!!))
    }
}