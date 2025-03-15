package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.MenuItemCassandraRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.OrderCassandraRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UserCassandraRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.*
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.stereotype.Component
import java.util.UUID

@Component("OrderRepoAdapter_Cassandra")
class OrderRepoAdapter(
    private val orderRepository: OrderCassandraRepository,
    private val userRepository: UserCassandraRepository,
    private val menuItemRepository: MenuItemCassandraRepository

) : OrderRepo {


    override fun saveOrder(order: IOrder): IOrder {
        val savedOrder = orderRepository.save(order.toOrderEntity())
        return getIOrderById(savedOrder.id!!)
    }

    override fun updateOrder(order: IOrder): IOrder {
        val orderEntityForSaving = order.toOrderEntity()
        val existingOrderEntity = orderRepository.findById(orderEntityForSaving.id!!)
            .orElseThrow { RuntimeException("Order not found with id: ${order.id!!}") }
        val savedOrder = orderRepository.save(orderEntityForSaving)
        return getIOrderById(orderEntityForSaving.id!!)
    }

    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        val ordersEntities = orderRepository.findByStatus(orderStatus)
        return ordersEntities.map { getIOrderById(it.id!!) }
    }

    override fun updateOrderStatus(orderId: Long, status: OrderStatus): IOrder {
        val orderEntity = orderRepository.findById(orderId.getUUIDFromLong())
            .orElseThrow { RuntimeException("Order not found with id : $orderId") }
        orderEntity.status=status
        val savedOrder=orderRepository.save(orderEntity)
        return getIOrderById(savedOrder.id!!)
    }


    override fun getOrdersByUser(user: IUser): List<IOrder> {
        val ordersEntities = orderRepository.findByUser(user.id!!.getUUIDFromLong())
        return ordersEntities.map { getIOrderById(it.id!!) }
    }


    public fun getIOrderById(orderId: UUID): IOrder {
        val orderEntity = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found with id : $orderId") }


        val userResult = userRepository.findById(orderEntity.userId)
            .map { it.toIUser() }.orElseThrow { RuntimeException("User not found with id: ${orderEntity.userId}") }


        val menuItems = orderEntity.menuItemsIds!!
            .map { menuItemId ->
                menuItemRepository.findById(menuItemId)
                    .map { it.toIMenuItem() }
                    .orElseThrow { RuntimeException("MenuItem not found with id: ${menuItemId}") }
            }
        return orderEntity.toIOrder(userResult, menuItems.toMutableList())
    }


}