package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb

import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.OrderEntity
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface OrderMongoRepository : MongoRepository<OrderEntity, UUID> {
    fun findByUserId(userId: UUID): List<OrderEntity>
    fun findByStatus(orderStatus: OrderStatus?): List<OrderEntity>

}