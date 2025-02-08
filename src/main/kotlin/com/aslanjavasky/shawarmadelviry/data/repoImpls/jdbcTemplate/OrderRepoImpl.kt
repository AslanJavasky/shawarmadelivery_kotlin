package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp

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
        //todo
        val sql = "SELECT * FROM orders WHERE user_id=?"
        return jdbcTemplate.query(sql, { rs, _ ->
            getOrderById(rs.getLong("id"))
        },user.id)
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
                WHERE O.status=?
                ORDER BY O.id
                
                """.trimIndent();

        return jdbcTemplate.query(sql, ResultSetExtractor{ rs ->
            val orderMap = linkedMapOf<Long,IOrder>()
            while (rs.next()){
                val orderId=rs.getLong("order_id")
                val order= orderMap.getOrPut(orderId){
                    try {
                        createOrderFromRS(rs).apply {
                            user=createUserFromRS(rs)
                            itemList= mutableListOf<IMenuItem>()
                        }
                    }catch (e:SQLException){
                        throw RuntimeException("Failed to get order by status")
                    }
                }
                order.itemList!!.add(createMenuItemFromRS(rs))
            }
            orderMap.values.toList()
        },
            orderStatus.name) ?: throw RuntimeException("Failed to get order by status")
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

        return jdbcTemplate.query(sql, { rs, _ ->
            Order().apply {
                id = rs.getLong("id")
                dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                status = OrderStatus.valueOf(rs.getString("status"))
                user = userRepoImpl.getUserById(rs.getLong("user_id"))
                totalPrice = rs.getBigDecimal("total_price")
                itemList = getMenuItemsForOrder(orderId)
            }
        }, orderId)[0] ?: throw RuntimeException("Failed to select order, no order with orderId:$orderId in the table \"orders\"")
    }


    private fun getMenuItemsForOrder(orderId: Long): MutableList<IMenuItem?> {
        //TODO
        val sql = "SELECT * FROM orders_menu_items WHERE order_id=?"
        return jdbcTemplate.query(sql, { rs, _ ->
            menuItemRepoImpl.getMenuItemById(rs.getLong("menu_item_id"))
        }, orderId)
    }

}