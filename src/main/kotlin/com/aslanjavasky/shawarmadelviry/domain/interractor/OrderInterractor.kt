package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo

open class OrderInterractor(
    private val repo: OrderRepo
) {
    fun createOrder(order: IOrder) = repo.saveOrder(order)
    fun changeOrder(order: IOrder) = repo.updateOrder(order)
    fun getOrdersByStatus(orderStatus: OrderStatus) = repo.getOrderByStatus(orderStatus)
    fun getOrderByUser(user: IUser) = repo.getOrdersByUser(user)
    fun updateOrderStatus(id: Long, status: OrderStatus) {
        repo.updateOrderStatus(id, status)
    }


}