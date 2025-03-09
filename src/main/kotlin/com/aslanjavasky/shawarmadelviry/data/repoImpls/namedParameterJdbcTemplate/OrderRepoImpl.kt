package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParameterJdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp

@Repository("ORwNPJT")
class OrderRepoImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : OrderRepo {

    @Transactional
    override fun saveOrder(order: IOrder): IOrder {
        val sqlOrder = "INSERT INTO orders(date_time, status, user_id, total_price) " +
                "VALUES( :date_time, :status, :user_id, :total_price);"
        val keyHolder = GeneratedKeyHolder()
        val params = MapSqlParameterSource()
            .addValue("date_time", Timestamp.valueOf(order.dateTime))
            .addValue("status", order.status!!.name)
            .addValue("user_id", order.user!!.id)
            .addValue("total_price", order.totalPrice)

        namedParameterJdbcTemplate.update(sqlOrder, params, keyHolder, arrayOf("id"))

        order.id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to save orders, no generated key")

        val sqlOrderMenuitems = "INSERT INTO orders_menu_items(order_id, menu_item_id) " +
                "VALUES( :order_id, :menu_item_id);"
        val batchArgs = order.itemList!!.map { item ->
            MapSqlParameterSource()
                .addValue("order_id", order.id)
                .addValue("menu_item_id", item.id)
        }.toTypedArray()
        namedParameterJdbcTemplate.batchUpdate(sqlOrderMenuitems, batchArgs)

        return order
    }


    @Transactional
    override fun updateOrder(order: IOrder): IOrder {
        val sql = "UPDATE orders SET date_time = :date_time, status = :status, " +
                "user_id = :user_id, total_price = :total_price WHERE id = :id"

        val params = MapSqlParameterSource()
            .addValue("date_time", Timestamp.valueOf(order.dateTime))
            .addValue("status", order.status!!.name)
            .addValue("user_id", order.user!!.id)
            .addValue("total_price", order.totalPrice)
            .addValue("id", order.id)

        val affectedRow = namedParameterJdbcTemplate.update(sql, params)
        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
        return order
    }

    @Transactional
    override fun getOrdersByUser(user: IUser): List<IOrder> {

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
                WHERE O.user_id = :user_id
                ORDER BY O.id
                """.trimIndent()

        return namedParameterJdbcTemplate.query(sql, MapSqlParameterSource("user_id", user.id),
            ResultSetExtractor{ rs ->
                val orderMap = linkedMapOf<Long, IOrder>()
                while (rs.next()) {
                    val orderId = rs.getLong("order_id")
                    val order = orderMap.getOrPut(orderId) {
                        try {
                            createOrderFromRS(rs).apply {
                                this.user = createUserFromRS(rs)
                                itemList = mutableListOf<IMenuItem>()
                            }
                        } catch (e: SQLException) {
                            throw RuntimeException("Failed to get order by status")
                        }
                    }
                    order.itemList!!.add(createMenuItemFromRS(rs))
                }
                orderMap.values.toList()
            }
        ) ?: throw RuntimeException("Failed to get order by status")
    }


    @Transactional
    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {

        val sql = """
                SELECT 
                    U.id AS user_id,
                    U.name AS user_name,
                    U.email,
                    U.password,
                    U.telegram,
                    U.phone, 
                    U.address,
                    OMI.menu_item_id,
                    MI.name as menu_item_name,
                    MI.menu_section,
                    MI.price,
                    OMI.order_id,
                    O.date_time,
                    O.status,
                    O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.status = :status 
                ORDER BY O.id
                
                """.trimIndent();

        return namedParameterJdbcTemplate.query(
            sql, MapSqlParameterSource("status", orderStatus.name), ResultSetExtractor { rs ->
                val orderMap = linkedMapOf<Long, IOrder>()
                while (rs.next()) {
                    val orderId = rs.getLong("order_id")
                    val order = orderMap.getOrPut(orderId) {
                        try {
                            createOrderFromRS(rs).apply {
                                user = createUserFromRS(rs)
                                itemList = mutableListOf<IMenuItem>()
                            }
                        } catch (e: SQLException) {
                            throw RuntimeException("Failed to get order by status")
                        }
                    }
                    order.itemList!!.add(createMenuItemFromRS(rs))
                }
                orderMap.values.toList()
            }
        ) ?: throw RuntimeException("Failed to get order by status")
    }

    @Throws(SQLException::class)
    private fun createMenuItemFromRS(rs: ResultSet): MenuItem {
        val menuItem = MenuItem()
        menuItem.id = rs.getLong("menu_item_id")
        menuItem.name = rs.getString("menu_item_name")
        menuItem.menuSection = MenuSection.valueOf(rs.getString("menu_section"))
        menuItem.price = rs.getBigDecimal("price")
        return menuItem
    }

    @Throws(SQLException::class)
    private fun createOrderFromRS(rs: ResultSet): IOrder {
        val newOrder: IOrder = Order()
        newOrder.id = rs.getLong("order_id")
        newOrder.dateTime = rs.getTimestamp("date_time").toLocalDateTime()
        newOrder.status = OrderStatus.valueOf(rs.getString("status"))
        newOrder.totalPrice = rs.getBigDecimal("total_price")
        return newOrder
    }

    @Throws(SQLException::class)
    private fun createUserFromRS(rs: ResultSet): IUser {
        val user: IUser = User()
        user.id = rs.getLong("user_id")
        user.name = rs.getString("user_name")
        user.email = rs.getString("email")
        user.password = rs.getString("password")
        user.telegram = rs.getString("telegram")
        user.phone = rs.getString("phone")
        user.address = rs.getString("address")
        return user
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus): IOrder {
        val sql = "UPDATE orders SET status = :status WHERE id = :id"
        val params = MapSqlParameterSource()
            .addValue("status", status.name)
            .addValue("id", id)
        val affectedRow = namedParameterJdbcTemplate.update(sql, params)
        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
        return getOrderById(id)!!
    }

    fun getOrderById(orderId: Long): IOrder? {
        val sql = """
                SELECT 
                    U.id AS user_id,
                    U.name AS user_name,
                    U.email,
                    U.password,
                    U.telegram,
                    U.phone, 
                    U.address,
                    OMI.menu_item_id,
                    MI.name as menu_item_name,
                    MI.menu_section,
                    MI.price,
                    OMI.order_id,
                    O.date_time,
                    O.status,
                    O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items MI ON MI.id=OMI.menu_item_id
                WHERE O.id = :id
                ORDER BY O.id
                
                """.trimIndent()

        return namedParameterJdbcTemplate.query(
            sql, MapSqlParameterSource("id", orderId), ResultSetExtractor { rs ->
                var order: IOrder? = null
                while (rs.next()) {
                    if (order == null) {
                        order = createOrderFromRS(rs).apply {
                            user = createUserFromRS(rs)
                            itemList = mutableListOf()
                        }
                    }
                    order.itemList!!.add(createMenuItemFromRS(rs))
                }
                order
            })
    }

}