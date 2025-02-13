package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.ORDERS
import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.ORDERS_MENU_ITEMS
import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.Orders
import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.OrdersMenuItems
import org.jooq.DSLContext
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

@Repository("ORwJOOQ")
class OrderRepoImpl(
    private val dslContext: DSLContext
) : OrderRepo {

    override fun saveOrder(order: IOrder): IOrder {

        val orderId = dslContext.insertInto(ORDERS)
            .set(ORDERS.DATE_TIME, order.dateTime)
            .set(ORDERS.STATUS, order.status!!.name)
            .set(ORDERS.USER_ID, order.user!!.id)
            .set(ORDERS.TOTAL_PRICE, order.totalPrice)
            .returningResult(ORDERS.ID)
            .fetchOne()
            ?.get(ORDERS.ID)

        order.id = orderId ?: throw RuntimeException("Failed to save orders, no generated key")

        val queryCollection=order.itemList!!.map { item ->
            dslContext.insertInto(ORDERS_MENU_ITEMS)
                .set(ORDERS_MENU_ITEMS.ORDER_ID, order.id)
                .set(ORDERS_MENU_ITEMS.MENU_ITEM_ID, item.id)
        }
        dslContext.batch(queryCollection).execute()
        return order
    }


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
                O.total_price
                FROM orders O
                JOIN users U ON O.user_id=U.id
                WHERE O.user_id= :user_id
                ORDER BY O.id
                """.trimIndent()

        return namedParameterJdbcTemplate.query(sql, MapSqlParameterSource("user_id", user.id), { rs, _ ->
            val order = createOrderFromRS(rs)
            order.user = createUserFromRS(rs)
            order
        })
    }


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