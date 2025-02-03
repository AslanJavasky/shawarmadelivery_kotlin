package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class OrderRepoImpl : OrderRepo {

    private val orders = mutableListOf<IOrder>()
    private var nextId = AtomicLong(1)

    override fun saveOrder(order: IOrder): IOrder {
        order.id = nextId.getAndIncrement()
        orders.add(order)
        return order
    }

    override fun updateOrder(order: IOrder): IOrder {
        val index = orders.indexOfFirst { it.id == order.id }
        if (index != -1) orders[index] = order
        return order
    }

    override fun getOrdersByUser(user: IUser): List<IOrder> {
        return orders.filter { it.user!!.id == user.id }
    }

    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        return orders.filter { it.status!!.name == orderStatus.name }
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus): IOrder {
        val order=orders.find { it.id == id }
        order?.let {
            it.status=status
        }
        return order!!
    }
}