package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo

class OrderRepoImpl : OrderRepo {

    private val orders = mutableListOf<Order>()

    override fun saveOrder(order: Order): Order {
        orders.add(order)
        return order
    }

    override fun updateOrder(order: Order): Order {
        val index = orders.indexOfFirst { it.id == order.id }
        if (index != -1) orders[index] = order
        return order
    }

    override fun getOrdersByUser(user: User): List<Order> {
        return orders.filter { it.user.id == user.id }
    }

    override fun getOrderByStatus(orderStatus: OrderStatus): List<Order> {
        return orders.filter { it.status.name == orderStatus.name }
    }
}