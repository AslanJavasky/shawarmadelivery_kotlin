package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toDeliveryEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIDelivery
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.DeliveryPSRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component("DeliveryRepoAdapter_PageSortING")
class DeliveryRepoAdapter(
    private val deliveryRepository: DeliveryPSRepository,
    @Qualifier("OrderRepoAdapter_PageSortING") private val orderRepoAdapter: OrderRepoAdapter,
) : DeliveryRepo {

    @Transactional
    override fun saveDelivery(delivery: IDelivery): IDelivery {
        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery(delivery.order!!)
    }

    @Transactional
    override fun updateDelivery(delivery: IDelivery): IDelivery {
        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery(delivery.order!!)
    }

    @Transactional
    override fun getDeliveryById(id: Long): IDelivery? {
        val deliveryEntity = deliveryRepository.findById(id)
            .orElseThrow { RuntimeException("Delivery not found with id: $id") }
        return deliveryEntity.toIDelivery(orderRepoAdapter.getIOrderById(deliveryEntity.orderId!!))
    }

    @Transactional
    fun getAllDeliveries(pageable: Pageable): Page<IDelivery> =
        deliveryRepository.findAll(pageable).map { getDeliveryById(it.id!!) }

    @Transactional
    fun getAllDeliveries(sort: Sort): List<IDelivery> =
        deliveryRepository.findAll(sort).map { getDeliveryById(it.id!!)!! }

    @Transactional
    fun getDeliveriesByAddressContaining(address: String, pageable: Pageable): Page<IDelivery> =
        deliveryRepository.findByAddressContaining(address, pageable).map { getDeliveryById(it.id!!) }

    @Transactional
    fun getDeliveriesByPhoneContaining(phone: String, pageable: Pageable): Page<IDelivery> =
        deliveryRepository.findByAddressContaining(phone, pageable).map { getDeliveryById(it.id!!) }

    @Transactional
    fun getDeliveriesByDateTimeBetweenOrderByDateTimeDesc(
        startDate: LocalDateTime, endDateTime: LocalDateTime, sort: Sort
    ): List<IDelivery> =
        deliveryRepository.findByDateTimeBetweenOrderByDateTimeDesc(startDate, endDateTime, sort)
            .map { getDeliveryById(it.id!!)!! }

    @Transactional
    fun getDeliveriesByOrder(order: IOrder, pageable: Pageable): Page<IDelivery> =
        deliveryRepository.findByOrderId(order.id!!, pageable).map { getDeliveryById(it.id!!) }


}