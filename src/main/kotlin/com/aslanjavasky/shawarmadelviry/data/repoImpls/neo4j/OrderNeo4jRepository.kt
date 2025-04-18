//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.OrderEntity
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.UserEntity
//import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
//import org.springframework.data.neo4j.repository.Neo4jRepository
//import org.springframework.stereotype.Repository
//import java.util.*
//
//
//@Repository
//interface OrderNeo4jRepository : Neo4jRepository<OrderEntity, UUID> {
//    fun findByUser(user: UserEntity): List<OrderEntity>
//    fun findByStatus(orderStatus: OrderStatus?): List<OrderEntity>
//
//}