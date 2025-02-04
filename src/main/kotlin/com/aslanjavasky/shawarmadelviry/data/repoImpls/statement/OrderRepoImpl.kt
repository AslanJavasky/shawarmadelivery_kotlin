package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import javax.sql.DataSource

@Repository("ORwPS")
class OrderRepoImpl(
    private val dataSource: DataSource,
    @Qualifier("MRwPS") private val menuItemRepoImpl: MenuItemRepo,
    @Qualifier("URwPS") private val userRepoImpl: UserRepoImpl,
) : OrderRepo {

    override fun saveOrder(order: IOrder): IOrder {
        val sqlOrder = "INSERT INTO orders(date_time, status, user_id, total_price) VALUES(?,?,?,?);"
        val sqlOrderMenuitems = "INSERT INTO orders_menu_items(order_id, menu_item_id) VALUES(?,?);"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setTimestamp(1, Timestamp.valueOf(order.dateTime))
                ps.setString(2, order.status!!.name)
                ps.setLong(3, order.user!!.id!!)
                ps.setBigDecimal(4, order.totalPrice)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to save order, no rows affected")

                ps.generatedKeys.use { rs ->
                    while (rs.next()) {
                        order.id = rs.getLong("id")
                    }
                }

            }
            connection.prepareStatement(sqlOrderMenuitems).use { ps ->
                for (item in order.itemList!!) {
                    ps.setLong(1, order.id!!)
                    ps.setLong(2, item!!.id)

                    val affectedRow = ps.executeUpdate()
                    if (affectedRow == 0) throw SQLException("Failed to save menuitem related to order, no rows affected")
                }
            }
        }
        return order
    }

    override fun updateOrder(order: IOrder): IOrder {
        val sql = "UPDATE orders SET date_time=?, status=?, user_id=?, total_price=?  WHERE id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->

                ps.setTimestamp(1, Timestamp.valueOf(order.dateTime))
                ps.setString(2, order.status!!.name)
                ps.setLong(3, order.user!!.id!!)
                ps.setBigDecimal(4, order.totalPrice)
                ps.setLong(5, order.id!!)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to update order, no rows affected")

            }
        }
        return order
    }

    override fun getOrdersByUser(user: IUser): List<IOrder> {
        val orders = mutableListOf<IOrder>()
        val sql = "SELECT * FROM orders WHERE user_id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, user.id!!)
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        val orderId = rs.getLong("id")
                        getOrderById(orderId)?.let { orders.add(it) }
                    }
                }
            }
        }
        return orders
    }


    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        val orders = mutableListOf<IOrder>()
        val sql = "SELECT * FROM orders WHERE status=?"
        return dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, orderStatus.name)
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        val orderId = rs.getLong("id")
                        getOrderById(orderId)?.let { orders.add(it) }
                    }
                }
                orders
            }
        }
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus): IOrder {
        val sql = "UPDATE orders SET status=? WHERE id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, status.name)
                ps.setLong(2, id)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to update order, no rows affected")
            }
            return getOrderById(id)
        }
    }

    fun getOrderById(orderId: Long): IOrder {
        val sql = "SELECT * FROM orders WHERE id=?"
        val sqlFromOrdersMenuItems = "SELECT * FROM orders_menu_items WHERE order_id=?"
        dataSource.connection.use { connection ->
            val order = Order()
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, orderId)

                ps.executeQuery().use { rs ->
                    while (rs.next()){
                        order.id = rs.getLong("id")
                        order.dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                        order.status = OrderStatus.valueOf(rs.getString("status"))
                        order.user = userRepoImpl.getUserById(rs.getLong("user_id"))
                        order.totalPrice = rs.getBigDecimal("total_price")
                    }
                }
            }
            connection.prepareStatement(sqlFromOrdersMenuItems).use { ps ->
                ps.setLong(1, orderId)
                val menuItems = mutableListOf<IMenuItem>()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        val menuItemId = rs.getLong("menu_item_id")
                        menuItemRepoImpl.getMenuItemById(menuItemId)?.let { menuItems.add(it) }
                    }
                }
                order.itemList = menuItems
            }
            return order
        }
    }
}