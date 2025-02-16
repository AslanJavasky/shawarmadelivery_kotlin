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
            connection.autoCommit = false
            try {
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
                        ps.setLong(2, item!!.id!!)
                        ps.addBatch()
                    }
                    val batchResults = ps.executeBatch()
                    for (result in batchResults) {
                        if (result == Statement.EXECUTE_FAILED) {
                            throw SQLException("Failed to save menuitem related to order, no rows affected")
                        }
                    }
                }
                connection.commit()
            }catch (e:SQLException){
                connection.rollback()
                e.printStackTrace()
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

        val sql = """
                SELECT
                  O.id AS order_id,
                  U.id AS user_id,
                  U.name AS user_name,
                  U.email,
                  U.password,
                  U.telegram,
                  U.phone,
                  U.address,
                  O.date_time,
                  O.status,
                  O.total_price,
                  MI.id AS menu_item_id,
                  MI.name AS menu_item_name,
                  MI.menu_section,
                  MI.price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON OMI.order_id=O.id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.user_id = ?
                ORDER BY O.id
                """.trimIndent()
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, user.id!!)
                ps.executeQuery().use { rs ->

                    while (rs.next()) {

                        val order: IOrder? = null
                        if (order == null) {
                            order?.id = rs.getLong("order_id")
                            order?.dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                            order?.status = OrderStatus.valueOf(rs.getString("status"))
                            order?.totalPrice = rs.getBigDecimal("total_price")

                            val _user: IUser = User()
                            _user.id = rs.getLong("user_id")
                            _user.address = rs.getString("address")
                            _user.email = rs.getString("email")
                            _user.name = rs.getString("user_name")
                            _user.password = rs.getString("password")
                            _user.phone = rs.getString("phone")
                            _user.telegram = rs.getString("telegram")
                            order?.user = _user

                            order?.itemList = ArrayList<IMenuItem>()
                        }
                        val menuItem: IMenuItem = MenuItem()
                        menuItem.id = rs.getLong("menu_item_id")
                        menuItem.menuSection = MenuSection.valueOf(rs.getString("menu_section"))
                        menuItem.name = rs.getString("menu_item_name")
                        menuItem.price = rs.getBigDecimal("price")

                        order!!.itemList!!.add(menuItem)

                        orders.add(order)
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
                    while (rs.next()) {
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