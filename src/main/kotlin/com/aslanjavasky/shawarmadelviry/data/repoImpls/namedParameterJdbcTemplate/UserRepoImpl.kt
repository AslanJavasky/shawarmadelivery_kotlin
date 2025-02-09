package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParameterJdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository("URwNPJT")
class UserRepoImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : UserRepo {
    override fun saveUser(user: IUser): IUser {

        val sql =
            "INSERT INTO users ( name, email, password, telegram, phone, address) " +
                    "VALUES(:name, :email, :password, :telegram, :phone, :address);"

        val keyHolder = GeneratedKeyHolder()
        namedParameterJdbcTemplate.update(sql, BeanPropertySqlParameterSource(user),keyHolder, arrayOf("id"))
        user.id = keyHolder.key!!.toLong()
        return user
    }

    override fun deleteUser(user: IUser) {
        val sql = "DELETE FROM users WHERE id = :id"

        val affectedRow = namedParameterJdbcTemplate.update(
            sql, MapSqlParameterSource("id",user.id))
        if (affectedRow == 0) throw RuntimeException("Failed to delete user, no rows affected")

    }


    override fun deleteUserByEmail(email: String) {
        val sql = "DELETE FROM users WHERE email = :email "
        val affectedRow = namedParameterJdbcTemplate.update(
            sql,MapSqlParameterSource("email",email))
        if (affectedRow == 0) throw RuntimeException("Failed to delete user, no rows affected")
    }


    override fun updateUser(user: IUser): IUser {

        val sql = "UPDATE users SET name = :name, email = :email, password = :password, " +
                "telegram = :telegram, phone = :phone, address = :address WHERE id = :id"
        val affectedRow =
            namedParameterJdbcTemplate.update(
                sql, BeanPropertySqlParameterSource(user)
            )
        if (affectedRow == 0) throw RuntimeException("Failed to update user, no rows affected")
        return user
    }


    override fun getUserByEmail(email: String): IUser? {
        val sql = "SELECT * FROM users WHERE email = :email"

        return namedParameterJdbcTemplate.queryForObject(sql,
            MapSqlParameterSource("email",email),
            BeanPropertyRowMapper.newInstance(User::class.java))


//            .findFirst().orElse(null)

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
        val sql = "SELECT * FROM users WHERE id = :id"

        return namedParameterJdbcTemplate.queryForStream(
            sql,
            MapSqlParameterSource("id",id),
            BeanPropertyRowMapper.newInstance(User::class.java))
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
