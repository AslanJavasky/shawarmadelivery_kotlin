package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIOrder
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toOrderEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("OrderRepoAdapter_CRUD")
class OrderRepoAdapter(
    @Qualifier("OrderRepoExtCrudRepo") private val orderRepository: OrderRepository,
    @Qualifier("UserRepoExtCrudRepo") private val userRepository: UserRepository,
    @Qualifier("MenuItemRepoExtCrudRepo") private val menuItemRepository: MenuItemRepository

) : OrderRepo {


    @Transactional
    override fun saveOrder(order: IOrder): IOrder {
        val savedOrder = orderRepository.save(order.toOrderEntity())
        order.itemList!!.forEach { orderRepository.insertToOrdersMenuItems(savedOrder.id!!, it.id!!) }
        return savedOrder.toIOrder(order.user!!, order.itemList!!)
    }

    @Transactional
    override fun updateOrder(order: IOrder): IOrder {
        val existingOrderEntity = orderRepository.findById(order.id!!)
            .orElseThrow { RuntimeException("Order not found with id: ${order.id!!}") }
        val updatedOrder = orderRepository.save(order.toOrderEntity())
        orderRepository.deleteMenuItemsIdByOrderId(existingOrderEntity.id!!)
        order.itemList!!.forEach { orderRepository.insertToOrdersMenuItems(existingOrderEntity.id!!, it.id!!) }
        return order
    }


    @Transactional
    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        return orderRepository.getOrdersByStatus(orderStatus).map { getIOrderById(it.id!!) }
    }

    @Transactional
    override fun updateOrderStatus(orderId: Long, status: OrderStatus): IOrder {
        orderRepository.updateOrderStatus(orderId, status)
        return getIOrderById(orderId)
    }

    @Transactional
    override fun getOrdersByUser(user: IUser): List<IOrder> {
        return orderRepository.getByUserId(user.id!!).map { getIOrderById(it.id!!, user) }
    }

    @Transactional
    public fun getIOrderById(orderId: Long, user: IUser? = null): IOrder {
        val orderEntity = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found with id : $orderId") }


        val userResult = user ?: userRepository.findById(orderEntity.userId)
            .map { it.toIUser() }.orElseThrow { RuntimeException("User not found with id: ${orderEntity.userId}") }


        val menuItems = orderRepository.getMenuItemsIdsByOrderId(orderEntity.id!!)
            .map { menuItemId ->
                menuItemRepository.findById(menuItemId)
                    .map { it.toIMenuItem() }
                    .orElseThrow { RuntimeException("MenuItem not found with id: ${menuItemId}") }
            }
        return orderEntity.toIOrder(userResult, menuItems.toMutableList())
    }


}