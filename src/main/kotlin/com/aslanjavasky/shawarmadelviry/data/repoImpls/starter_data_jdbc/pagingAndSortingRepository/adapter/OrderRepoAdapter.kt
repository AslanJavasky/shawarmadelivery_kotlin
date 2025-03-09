package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIOrder
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toOrderEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.OrderPSRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component("OrderRepoAdapter_PageSortING")
class OrderRepoAdapter(
    private val orderRepository: OrderPSRepository,
    private val userRepository: UserPSRepository,
    private val menuItemRepository: MenuItemPSRepository

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
//        return orderRepository.getOrdersByStatus(orderStatus).map { getIOrderById(it.id!!) }
        val sort=Sort.by(Sort.Direction.DESC, "date_time")
        return getAOrdersByStatusOrderByDateTimeDesc(orderStatus,sort)
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

    @Transactional
    fun getAllOrders(pageable: Pageable): Page<IOrder> =
        orderRepository.findAll(pageable).map { getIOrderById(it.id!!) }

    @Transactional
    fun getAllOrders(sort: Sort): List<IOrder> =
        orderRepository.findAll(sort).map { getIOrderById(it.id!!) }

    @Transactional
    fun getOrdersByUser(user: IUser, pageable: Pageable): Page<IOrder> =
        orderRepository.findByUserId(user.id!!, pageable).map { getIOrderById(it.id!!) }

    @Transactional
    fun getOrdersByStatus(status: OrderStatus, pageable: Pageable): Page<IOrder> =
        orderRepository.findByStatus(status, pageable).map { getIOrderById(it.id!!) }

    @Transactional
    fun getAOrdersByStatusOrderByDateTimeDesc(status: OrderStatus, sort: Sort): List<IOrder> =
        orderRepository.findByStatusOrderByDateTimeDesc(status, sort).map { getIOrderById(it.id!!) }

    @Transactional
    fun getOrdersByUserAndStatus(user: IUser, status: OrderStatus, pageable: Pageable): Page<IOrder> =
        orderRepository.findByUserIdAndStatus(user.id!!, status, pageable).map { getIOrderById(it.id!!) }

    @Transactional
    fun getOrdersByTotalPriceGTE(totalPrice: BigDecimal, pageable: Pageable): Page<IOrder> =
        orderRepository.findByTotalPriceGreaterThanEqual(totalPrice, pageable).map { getIOrderById(it.id!!) }

    @Transactional
    fun getOrdersByTotalPriceLTE(totalPrice: BigDecimal, pageable: Pageable): Page<IOrder> =
        orderRepository.findByTotalPriceLessThanEqualOrderByDateTimeAsc(totalPrice, pageable)
            .map { getIOrderById(it.id!!) }
}