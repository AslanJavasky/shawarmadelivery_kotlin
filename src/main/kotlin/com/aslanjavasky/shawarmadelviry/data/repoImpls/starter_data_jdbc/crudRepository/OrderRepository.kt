package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.OrderEntity
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("OrderRepoExtCrudRepo")
interface OrderRepository : CrudRepository<OrderEntity, Long> {

    @Modifying
    @Query("UPDATE orders SET status = :status WHERE id = :id")
    fun updateOrderStatus(@Param("id") orderId: Long, @Param("status") status: OrderStatus): Int

    @Query("SELECT * FROM orders WHERE user_id = :user_id")
    fun getByUserId(@Param("user_id") userId: Long): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE status = :status")
    fun getOrdersByStatus(@Param("status") status: OrderStatus): List<OrderEntity>

    @Modifying
    @Query("INSERT INTO orders_menu_items (order_id, menu_item_id) VALUES (:order_id, :menu_item_id)")
    fun insertToOrdersMenuItems(@Param("order_id") orderId: Long, @Param("menu_item_id") menuItemId: Long)

    @Query("SELECT menu_item_id FROM orders_menu_items WHERE order_id = :order_id")
    fun getMenuItemsIdsByOrderId(@Param("order_id") orderId: Long): List<Long>

    @Query("DELETE FROM orders_menu_items WHERE order_id = :order_id")
    fun deleteMenuItemsIdByOrderId(@Param("order_id") orderId: Long)

}