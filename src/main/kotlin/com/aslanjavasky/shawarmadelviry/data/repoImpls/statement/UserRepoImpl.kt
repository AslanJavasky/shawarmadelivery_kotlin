package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Logger
import javax.sql.DataSource

@Repository("URwPS")
class UserRepoImpl(
    private val dataSource: DataSource
) : UserRepo {
    override fun saveUser(user: IUser): IUser {

        val sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?)"
        return dataSource.connection.use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setString(1, user.name)
                ps.setString(2, user.email)
                ps.setString(3, user.password)
                ps.setString(4, user.telegram)
                ps.setString(5, user.phone)
                ps.setString(6, user.address)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to save user, no rows affected")

                ps.generatedKeys.use { rs ->
                    while (rs.next()) {
                        user.id = rs.getLong("id")
                    }
                }
                user
            }
        }
    }

    override fun deleteUser(user: IUser) {
        val sql = "DELETE FROM users WHERE id=?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                user.id?.let {
                    ps.setLong(1, it)
                    val affectedRow = ps.executeUpdate()
                    if (affectedRow == 0) throw SQLException("Failed to delete user, no rows affected")
                }
            }
        }
    }

    override fun deleteUserByEmail(email: String) {
        val sql = "DELETE FROM users WHERE email = ? "
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, email)
                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to delete user, no rows affected")
            }
        }
    }

    override fun updateUser(user: IUser): IUser {

        val sql = "UPDATE users SET name=?, email=?, password=?, telegram=?, phone=?, address=? WHERE id=?"
        return dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, user.name)
                ps.setString(2, user.email)
                ps.setString(3, user.password)
                ps.setString(4, user.telegram)
                ps.setString(5, user.phone)
                ps.setString(6, user.address)
                ps.setLong(7, user.id!!)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to update user, no rows affected")

                user
            }
        }
    }


    override fun getUserByEmail(email: String): IUser? {
        val sql = "SELECT * FROM users WHERE email=?"
        return dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, email)
                val user = User()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        user.id = rs.getLong("id")
                        user.name = rs.getString("name")
                        user.email = rs.getString("email")
                        user.password = rs.getString("password")
                        user.phone = rs.getString("phone")
                        user.address = rs.getString("address")
                        user.telegram = rs.getString("telegram")
                    }
                    user
                }
            }
        }
    }

    fun getUserById(userId: Long): IUser {
        val sql = "SELECT * FROM users WHERE id=?"
        return dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, userId)
                val user = User()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        user.id = rs.getLong("id")
                        user.name = rs.getString("name")
                        user.email = rs.getString("email")
                        user.password = rs.getString("password")
                        user.telegram = rs.getString("telegram")
                        user.phone = rs.getString("phone")
                        user.address = rs.getString("address")
                    }
                }
                user
            }
        }
    }
}
