package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo

open class OrderInterractor(
    private val repo: OrderRepo
) {
    fun createOrder(order: Order) = repo.saveOrder(order)
    fun changeOrder(order: Order) = repo.updateOrder(order)
    fun getOrdersByStatus(orderStatus: OrderStatus) = repo.getOrderByStatus(orderStatus)
    fun getOrderByUser(user: User) = repo.getOrdersByUser(user)
    fun updateOrderStatus(id: Long, status: OrderStatus) {
        repo.updateOrderStatus(id, status)
    }


}