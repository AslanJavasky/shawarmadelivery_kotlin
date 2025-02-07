package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository("URwJT")
class UserRepoImpl(
    private val jdbcTemplate: JdbcTemplate
) : UserRepo {
    override fun saveUser(user: IUser): IUser {

        val sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?)"

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(PreparedStatementCreator { con: Connection ->
            val ps = con.prepareStatement(sql, arrayOf("id"))
            ps.setString(1, user.name)
            ps.setString(2, user.email)
            ps.setString(3, user.password)
            ps.setString(4, user.telegram)
            ps.setString(5, user.phone)
            ps.setString(6, user.address)
            ps
        }, keyHolder)

        user.id = keyHolder.key!!.toLong()
        return user
    }

    override fun deleteUser(user: IUser) {
        val sql = "DELETE FROM users WHERE id=?"

        val affectedRow = jdbcTemplate.update(sql, user.id)
        if (affectedRow == 0) throw RuntimeException("Failed to delete user, no rows affected")

    }


    override fun deleteUserByEmail(email: String) {
        val sql = "DELETE FROM users WHERE email = ? "
        val affectedRow = jdbcTemplate.update(sql, email)
        if (affectedRow == 0) throw RuntimeException("Failed to delete user, no rows affected")
    }


    override fun updateUser(user: IUser): IUser {

        val sql = "UPDATE users SET name=?, email=?, password=?, telegram=?, phone=?, address=? WHERE id=?"
        val affectedRow =
            jdbcTemplate.update(
                sql, user.name, user.email, user.password, user.telegram, user.phone, user.address, user.id!!
            )
        if (affectedRow == 0) throw RuntimeException("Failed to update user, no rows affected")
        return user
    }


    override fun getUserByEmail(email: String): IUser? {
        val sql = "SELECT * FROM users WHERE email=?"

        return jdbcTemplate.queryForStream(sql, BeanPropertyRowMapper.newInstance(User::class.java), email)
            .findFirst().orElse(null)

//        return jdbcTemplate.queryForObject(sql, arrayOf(email)) { rs, _ ->
//            User().apply {
//                id = rs.getLong("id")
//                name = rs.getString("name")
//                this.email = rs.getString("email")
//                password = rs.getString("password")
//                phone = rs.getString("phone")
//                address = rs.getString("address")
//                telegram = rs.getString("telegram")
//            }
//        }
    }


    fun getUserById(id: Long): IUser? {
        val sql = "SELECT * FROM users WHERE id=?"

        return jdbcTemplate.queryForStream(
            sql, BeanPropertyRowMapper.newInstance(User::class.java), id)
            .findFirst().orElse(null)


//        return jdbcTemplate.queryForObject(sql, arrayOf(userId)) { rs, _ ->
//            User().apply {
//                id = rs.getLong("id")
//                name = rs.getString("name")
//                email = rs.getString("email")
//                password = rs.getString("password")
//                telegram = rs.getString("telegram")
//                phone = rs.getString("phone")
//                address = rs.getString("address")
//            }
//
//        }
    }
}
