package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import javax.sql.DataSource

@Repository("DRwJT")
class DeliveryRepoImpl(
    private val jdbcTemplate: JdbcTemplate,
    @Qualifier("ORwJT") private val orderRepoImpl: OrderRepoImpl
) : DeliveryRepo {
    override fun saveDelivery(delivery: IDelivery): IDelivery {
        val sql = "INSERT INTO deliveries(address, phone, date_time, order_id) VALUES(?,?,?,?)"

        val keyHolder = GeneratedKeyHolder()
        val affectedRow = jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, arrayOf("id"))
            ps.setString(1, delivery.address)
            ps.setString(2, delivery.phone)
            ps.setTimestamp(3, Timestamp.valueOf(delivery.dateTime))
            ps.setLong(4, delivery.order!!.id!!)
            ps
        }, keyHolder)
        if (affectedRow == 0) throw RuntimeException("Failed to save delivery, no row affected")
        delivery.id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to save delivery, no generated key")
        return delivery
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        val sql = "UPDATE deliveries SET address= ? , phone=? , date_time= ?, order_id=? WHERE id=?"

        val affectedRow = jdbcTemplate.update(
            sql,
            delivery.address, delivery.phone, Timestamp.valueOf(delivery.dateTime),
            delivery.order!!.id!!, delivery.id!!
        )
        if (affectedRow == 0) throw RuntimeException("Failed to update delivery, no rows affected")
        return delivery
    }

    override fun getDeliveryById(id: Long): IDelivery? {
        val sql = "SELECT * FROM deliveries WHERE id=?"

        return jdbcTemplate.query(sql, { rs, _ ->
            Delivery().apply {
                this.id = rs.getLong("id")
                address = rs.getString("address")
                phone = rs.getString("phone")
                dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                order = orderRepoImpl.getOrderById(rs.getLong("order_id"))
            }
        }, id)[0] ?: null
    }
}