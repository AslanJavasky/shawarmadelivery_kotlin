package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class OrderRepoImpl : OrderRepo {

    private val orders = mutableListOf<Order>()
    private var nextId = AtomicLong(1)

    override fun saveOrder(order: Order): Order {
        order.id = nextId.getAndIncrement()
        orders.add(order)
        return order
    }

    override fun updateOrder(order: Order): Order {
        val index = orders.indexOfFirst { it.id == order.id }
        if (index != -1) orders[index] = order
        return order
    }

    override fun getOrdersByUser(user: User): List<Order> {
        return orders.filter { it.user!!.id == user.id }
    }

    override fun getOrderByStatus(orderStatus: OrderStatus): List<Order> {
        return orders.filter { it.status!!.name == orderStatus.name }
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus): Order {
        val order=orders.find { it.id == id }
        order?.let {
            it.status=status
        }
        return order!!
    }
}