//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.getUUIDFromLong
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.DeliveryNeo4jRepository
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.OrderNeo4jRepository
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toDeliveryEntity
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toIDelivery
//import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
//import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
//import org.springframework.stereotype.Component
//import org.springframework.transaction.annotation.Transactional
//
//@Component("DeliveryRepoAdapter_Neo4j")
//class DeliveryRepoAdapter(
//    private val deliveryRepository: DeliveryNeo4jRepository,
//    private val orderRepository: OrderNeo4jRepository,
//) : DeliveryRepo {
//
//    @Transactional
//    override fun saveDelivery(delivery: IDelivery): IDelivery {
//
//        val deliveryEntity = delivery.toDeliveryEntity()
//
//        deliveryEntity.order.let {
//            val orderEntity = orderRepository.findById(deliveryEntity.order.id!!)
//                .orElseThrow { RuntimeException("Order not found with id: ${deliveryEntity.order.id!!}") }
//            deliveryEntity.order = orderEntity
//        }
//
//        return deliveryRepository.save(deliveryEntity).toIDelivery()
//    }
//
//    @Transactional
//    override fun updateDelivery(delivery: IDelivery): IDelivery {
//        return deliveryRepository.save(delivery.toDeliveryEntity()).toIDelivery()
//    }
//
//    @Transactional
//    override fun getDeliveryById(id: Long): IDelivery? {
//        return deliveryRepository.findById(id.getUUIDFromLong())
//            .orElseThrow { RuntimeException("Delivery not found with id: $id") }.toIDelivery()
//    }
//}