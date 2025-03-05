package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {

    fun findByUser(user: UserEntity?): List<OrderEntity>

    fun findByStatusOrderByDateTimeDesc(orderStatus: OrderStatus?): List<OrderEntity>

}