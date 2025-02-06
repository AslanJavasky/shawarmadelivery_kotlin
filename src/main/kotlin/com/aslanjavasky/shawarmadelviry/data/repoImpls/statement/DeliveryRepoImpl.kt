package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp
import javax.sql.DataSource

@Repository("DRwPS")
class DeliveryRepoImpl(
    private val dataSource: DataSource,
    @Qualifier("ORwPS") private val orderRepoImpl: OrderRepoImpl
) : DeliveryRepo {
    override fun saveDelivery(delivery: IDelivery): IDelivery {
        val sql = "INSERT INTO deliveries(address, phone, date_time, order_id) VALUES(?,?,?,?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setString(1, delivery.address)
                ps.setString(2, delivery.address)
                ps.setTimestamp(3, Timestamp.valueOf(delivery.dateTime))
                ps.setLong(4, delivery.order!!.id!!)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to save delivery, no rows affected")

                ps.generatedKeys.use { rs ->
                    while(rs.next()){
                        delivery.id = rs.getLong("id")
                    }

                }

            }
        }
        return delivery
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {
        val sql = "UPDATE deliveries SET address= ? , phone=? , date_time= ?, order_id=? WHERE id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, delivery.address)
                ps.setString(2, delivery.phone)
                ps.setTimestamp(3, Timestamp.valueOf(delivery.dateTime))
                ps.setLong(4, delivery.order!!.id!!)
                ps.setLong(5, delivery.id!!)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to update delivery, no rows affected")
            }
        }
        return delivery
    }

    override fun getDeliveryById(id: Long): IDelivery? {
        val sql = "SELECT * FROM deliveries WHERE id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, id)

                val delivery = Delivery()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        delivery.id = rs.getLong("id")
                        delivery.address = rs.getString("address")
                        delivery.phone = rs.getString("phone")
                        delivery.dateTime = rs.getTimestamp("date_time").toLocalDateTime()
                        delivery.order = orderRepoImpl.getOrderById(rs.getLong("order_id"))
                    }
                }
                return delivery
            }
        }
    }
}