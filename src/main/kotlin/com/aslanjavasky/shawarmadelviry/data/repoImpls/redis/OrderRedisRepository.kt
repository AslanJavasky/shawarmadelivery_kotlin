//package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.OrderEntity
//import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
//import org.springframework.data.repository.CrudRepository
//import org.springframework.stereotype.Repository
//import java.util.*
//
//
//@Repository
//interface OrderRedisRepository : CrudRepository<OrderEntity, UUID> {
//    fun findByUserId(userId: UUID): List<OrderEntity>
//    fun findByStatus(orderStatus: OrderStatus?): List<OrderEntity>
//
//}