package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.DeliveryJpaRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.OrderJpaRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toDeliveryEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toIDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("DeliveryRepoAdapter_JPA")
class DeliveryRepoAdapter(
    private val deliveryRepository: DeliveryJpaRepository,
    private val orderRepository: OrderJpaRepository,
) : DeliveryRepo {

    @Transactional
    override fun saveDelivery(delivery: IDelivery): IDelivery {

        val deliveryEntity = delivery.toDeliveryEntity()

        deliveryEntity.order.let {
            val orderEntity = orderRepository.findById(deliveryEntity.order.id!!)
                .orElseThrow { RuntimeException("Order not found with id: ${deliveryEntity.order.id!!}") }
            deliveryEntity.order = orderEntity
        }

        return deliveryRepository.save(deliveryEntity).toIDelivery()
    }

    @Transactional
    override fun updateDelivery(delivery: IDelivery): IDelivery {
        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery()
    }

    @Transactional
    override fun getDeliveryById(id: Long): IDelivery? {
        return deliveryRepository.findById(id)
            .orElseThrow { RuntimeException("Delivery not found with id: $id") }.toIDelivery()
    }
}