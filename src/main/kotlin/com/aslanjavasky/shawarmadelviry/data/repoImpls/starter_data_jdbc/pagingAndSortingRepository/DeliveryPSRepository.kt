package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.DeliveryRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface DeliveryPSRepository:PagingAndSortingRepository<DeliveryEntity, Long> ,
                                DeliveryRepository{
    override fun findAll(pageable: Pageable): Page<DeliveryEntity>

    override fun findAll(sort: Sort): MutableIterable<DeliveryEntity>

    fun findByAddressContaining(address:String, pageable: Pageable) : Page<DeliveryEntity>

    fun findByPhoneContaining(phone:String, pageable: Pageable) : Page<DeliveryEntity>

    fun findByDateTimeBetweenOrderByDateTimeDesc(
        startDate: LocalDateTime, endDate:LocalDateTime, sort:Sort) : List<DeliveryEntity>

    fun findByOrderId(orderId:Long, pageable: Pageable):Page<DeliveryEntity>


}