package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.OrderEntity
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface OrderPSRepository : PagingAndSortingRepository<OrderEntity, Long>,
    OrderRepository {

    override fun findAll(pageable: Pageable): Page<OrderEntity>

    override fun findAll(sort: Sort): List<OrderEntity>

    fun findByUserId(userId: Long, pageable: Pageable): Page<OrderEntity>

    fun findByStatus(status: OrderStatus, pageable: Pageable): Page<OrderEntity>

    fun findByStatusOrderByDateTimeDesc(status: OrderStatus, sort: Sort): List<OrderEntity>

    fun findByUserIdAndStatus(userId: Long, status: OrderStatus, pageable: Pageable): Page<OrderEntity>

    fun findByTotalPriceGreaterThanEqual(totalPrice: BigDecimal, pageable: Pageable): Page<OrderEntity>

    fun findByTotalPriceLessThanEqualOrderByDateTimeAsc(totalPrice: BigDecimal, pageable: Pageable): Page<OrderEntity>


}