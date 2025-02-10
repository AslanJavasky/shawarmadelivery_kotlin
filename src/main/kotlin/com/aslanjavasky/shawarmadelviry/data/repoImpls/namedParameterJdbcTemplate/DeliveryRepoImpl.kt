package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParameterJdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp

@Repository("DRwNPJT")
class DeliveryRepoImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
) : DeliveryRepo {
    override fun saveDelivery(delivery: IDelivery): IDelivery {
        val sql =
            "INSERT INTO deliveries(address, phone, date_time, order_id) " +
                                          "VALUES( :address, :phone, :date_time, :order_id)"

        val params = MapSqlParameterSource().addValue("address", delivery.address).addValue("phone", delivery.phone)
            .addValue("date_time", delivery.dateTime).addValue("order_id", delivery.order!!.id)

        val keyHolder = GeneratedKeyHolder()
        val affectedRow = namedParameterJdbcTemplate.update(sql, params, keyHolder, arrayOf("id"))
        if (affectedRow == 0) throw RuntimeException("Failed to save delivery, no row affected")
        delivery.id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to save delivery, no generated key")
        return delivery
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        val sql =
            "UPDATE deliveries SET address= :address , phone = :phone , " +
                    "date_time = :date_time, order_id = :order_id WHERE id = :id"

        val params = MapSqlParameterSource().addValue("address", delivery.address).addValue("phone", delivery.phone)
            .addValue("date_time", delivery.dateTime).addValue("order_id", delivery.order!!.id)
            .addValue("id", delivery.id)

        val affectedRow = namedParameterJdbcTemplate.update(sql, params)
        if (affectedRow == 0) throw RuntimeException("Failed to update delivery, no rows affected")
        return delivery
    }

    override fun getDeliveryById(id: Long): IDelivery? {
        val sql = """
                SELECT 
                	D.id AS delivery_id,
                	D.date_time AS delivery_date_time,
                	D.order_id,
                	O.date_time AS order_date_time,
                	O.status,
                	O.user_id,
                	O.total_price,
                	U.name AS user_name,
                	U.email,
                	U.password,
                	U.telegram,
                	U.phone,
                	U.address,
                	OMI.menu_item_id,
                	MI.name AS menu_item_name,
                	MI.menu_section,
                	MI.price
                FROM deliveries D 
                JOIN orders O ON D.order_id=O.id
                JOIN users U ON U.id=O.user_id 
                JOIN orders_menu_items OMI ON O.id=OMI.order_id
                JOIN menu_items	MI ON OMI.menu_item_id=MI.id
                WHERE D.id = :id
                ORDER BY D.id
                
                """.trimIndent()

        return namedParameterJdbcTemplate.query(sql, MapSqlParameterSource("id",id) ,
            ResultSetExtractor { rs ->
            var delivery: IDelivery? = null
            while (rs.next()) {
                if (delivery == null) {
                    delivery = createDeliveryFromRS(rs).apply {
                        order = createOrderFromRS(rs).apply {
                            user = createUserFromRS(rs)
                            itemList = mutableListOf()
                        }
                    }
                }
                delivery.order!!.itemList!!.add(createMenuItemFromRS(rs))
            }
            delivery ?: throw RuntimeException("No delivery found with id:$id")
        })
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
        newOrder.dateTime = rs.getTimestamp("order_date_time").toLocalDateTime()
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

    @Throws(SQLException::class)
    private fun createDeliveryFromRS(rs: ResultSet): IDelivery {
        val delivery: IDelivery = Delivery()
        delivery.id = rs.getLong("delivery_id")
        delivery.address = rs.getString("address")
        delivery.phone = rs.getString("phone")
        delivery.dateTime = rs.getTimestamp("delivery_date_time").toLocalDateTime()
        return delivery
    }
}