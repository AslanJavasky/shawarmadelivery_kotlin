package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.*

interface OrderRepo {
    fun saveOrder(order: IOrder): IOrder
    fun updateOrder(order: IOrder): IOrder
    fun getOrdersByUser(user: IUser): List<IOrder>
    fun getOrderByStatus(orderStatus: OrderStatus): List<IOrder>
    fun updateOrderStatus(id: Long, status: OrderStatus): IOrder
}