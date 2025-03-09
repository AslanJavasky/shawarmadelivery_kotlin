package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.MenuItemJpaRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.OrderJpaRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.UserJpaRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.*
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("OrderRepoAdapter_JPA")
class OrderRepoAdapter(
    private val orderRepository: OrderJpaRepository,
    private val userRepository: UserJpaRepository,
    private val menuItemRepository: MenuItemJpaRepository

) : OrderRepo {


    @Transactional
    override fun saveOrder(order: IOrder): IOrder {

        val orderEntity = order.toOrderEntity()

        if (orderEntity.user!!.id != null) {
            val userEntity = userRepository.findById(orderEntity.user!!.id!!)
                .orElseThrow { RuntimeException("User not found with id: ${order.user!!.id!!}") }
            orderEntity.user = userEntity
        }

        val itemList = orderEntity.itemList?.map { menuItem ->
            menuItemRepository.findById(menuItem.id!!)
                .orElseThrow { RuntimeException("MenuItem not found with id: ${menuItem.id!!}") }
        }?.toMutableList()

        orderEntity.itemList = itemList

        return orderRepository.save(orderEntity).toIOrder()
    }

    @Transactional
    override fun updateOrder(order: IOrder): IOrder {
        val existingOrderEntity = orderRepository.findById(order.id!!)
            .orElseThrow { RuntimeException("Order not found with id: ${order.id!!}") }
        return orderRepository.save(order.toOrderEntity()).toIOrder()
    }


    @Transactional
    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        return orderRepository.findByStatusOrderByDateTimeDesc(orderStatus).map { it.toIOrder() }
    }

    @Transactional
    override fun updateOrderStatus(orderId: Long, status: OrderStatus): IOrder {
        val orderEntity = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found with id : $orderId") }
        orderEntity.status = status
        return orderRepository.save(orderEntity).toIOrder()
    }

    @Transactional
    override fun getOrdersByUser(user: IUser): List<IOrder> {
        return orderRepository.findByUser(user.toUserEntity()).map { it.toIOrder() }
    }

    @Transactional
    fun getIOrderById(orderId: Long): IOrder {
        val orderEntity = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found with id : $orderId") }

        return orderEntity.toIOrder()
    }


}