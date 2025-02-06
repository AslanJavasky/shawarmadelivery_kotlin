package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import javax.sql.DataSource

@Repository("ORwJT")
class OrderRepoImpl(
    private val jdbcTemplate: JdbcTemplate,
    @Qualifier("MRwJT") private val menuItemRepoImpl: MenuItemRepo,
    @Qualifier("URwJT") private val userRepoImpl: UserRepoImpl,
) : OrderRepo {

    override fun saveOrder(order: IOrder): IOrder {
        val sqlOrder = "INSERT INTO orders(date_time, status, user_id, total_price) VALUES(?,?,?,?);"
        val sqlOrderMenuitems = "INSERT INTO orders_menu_items(order_id, menu_item_id) VALUES(?,?);"

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(PreparedStatementCreator { connection ->
            val ps = connection.prepareStatement(sqlOrder, arrayOf("id"))
            ps.setTimestamp(1, Timestamp.valueOf(order.dateTime))
            ps.setString(2, order.status!!.name)
            ps.setLong(3, order.user!!.id!!)
            ps.setBigDecimal(4, order.totalPrice)
            ps
        }, keyHolder)

        order.id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to save orders, no generated key")

        val batchArgs = order.itemList!!.map { arrayOf(order.id, it!!.id) }
        jdbcTemplate.batchUpdate(sqlOrderMenuitems, batchArgs)

        return order
    }


    override fun updateOrder(order: IOrder): IOrder {
        val sql = "UPDATE orders SET date_time=?, status=?, user_id=?, total_price=?  WHERE id=?"

        val affectedRow = jdbcTemplate.update(sql) { ps ->
            ps.setTimestamp(1, Timestamp.valueOf(order.dateTime))
            ps.setString(2, order.status!!.name)
            ps.setLong(3, order.user!!.id!!)
            ps.setBigDecimal(4, order.totalPrice)
            ps.setLong(5, order.id!!)
        }

        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
        return order
    }

    override fun getOrdersByUser(user: IUser): List<IOrder> {
        val sql = "SELECT * FROM orders WHERE user_id=?"
        return jdbcTemplate.query(sql, arrayOf(user.id)) { rs, _ ->
            getOrderById(rs.getLong("id"))
        }
    }


    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
        val sql = "SELECT * FROM orders WHERE status=?"
        return jdbcTemplate.query(sql, arrayOf(orderStatus.name)) { rs, _ ->
            getOrderById(rs.getLong("id"))
        }
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus): IOrder {
        val sql = "UPDATE orders SET status=? WHERE id=?"
        val affectedRow = jdbcTemplate.update(sql) { ps ->
            ps.setString(1, status.name)
            ps.setLong(2, id)
        }
        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
        return getOrderById(id)
    }

    fun getOrderById(orderId: Long): IOrder {
        val sql = "SELECT * FROM orders WHERE id=?"

        return jdbcTemplate.queryForObject(sql, arrayOf(orderId)) { rs, _ ->
            Order().apply {
                id = rs.getLong("id")
                dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                status = OrderStatus.valueOf(rs.getString("status"))
                user = userRepoImpl.getUserById(rs.getLong("user_id"))
                totalPrice = rs.getBigDecimal("total_price")
                itemList = getMenuItemsForOrder(orderId)
            }
        } ?: throw RuntimeException("Failed to select order, no order with orderId:$orderId in the table \"orders\"")
    }


    private fun getMenuItemsForOrder(orderId: Long): List<IMenuItem?> {
        val sql = "SELECT * FROM orders_menu_items WHERE order_id=?"
        return jdbcTemplate.query(sql, arrayOf(orderId)) { rs, _ ->
            menuItemRepoImpl.getMenuItemById(rs.getLong("menu_item_id"))
        }
    }

}