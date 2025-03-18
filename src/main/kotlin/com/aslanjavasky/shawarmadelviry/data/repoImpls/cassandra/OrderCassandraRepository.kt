package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.OrderEntity
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface OrderCassandraRepository : CassandraRepository<OrderEntity, UUID> {
    @Query("SELECT * FROM orders WHERE userid = ?0 ALLOW FILTERING")
    fun findByUser(userId: UUID): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE status = ?0 ALLOW FILTERING")
    fun findByStatus(orderStatus: OrderStatus?): List<OrderEntity>

}