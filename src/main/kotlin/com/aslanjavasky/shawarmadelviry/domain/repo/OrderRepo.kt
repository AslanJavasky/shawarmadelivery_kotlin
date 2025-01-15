package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User

interface OrderRepo {
    fun saveOrder(order:Order):Order
    fun updateOrder(order: Order):Order
    fun getOrdersByUser(user: User):List<Order>
    fun getOrderByStatus(orderStatus: OrderStatus):List<Order>
}