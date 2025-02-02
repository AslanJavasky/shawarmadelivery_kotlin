package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.stereotype.Repository
import java.sql.SQLException
import javax.sql.DataSource

@Repository("URwS")
class UserRepoImpl(
    private val dataSource: DataSource
) : UserRepo {
    override fun saveUser(user: IUser): IUser {
        return try {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    val sql = ("INSERT INTO users(name, email,password,telegram,phone,address) VALUES(" +
                            "'" + user.name + "','" + user.email + "','" + user.password + "','" +
                            user.telegram + "','" + user.phone + "','" + user.address + "'" +
                            ");")
                    statement.executeUpdate(sql)
                }
            }
            user
        } catch (e: SQLException) {
            User()
        }
    }

    override fun deleteUser(user: IUser) {
        try {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->

                    val sql="DELETE FROM users WHERE id=${user.id}"
                    statement.executeUpdate(sql)
                }
            }
        }catch (e:SQLException){
            e.printStackTrace()
        }
    }

    override fun updateUser(user: IUser): IUser {
        return try {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    val sql=
                        "UPDATE users SET name='${user.name}', email='${user.email}',password='${user.password}'" +
                            "telegram='${user.telegram}',phone='${user.phone}',address='${user.address}'" +
                                "WHERE id=${user.id};"
                    statement.executeUpdate(sql)
                    user
                }
            }
        }catch (e:SQLException){
            User()
        }
    }

    override fun getUserByEmail(email: String): IUser? {
        return try {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    val sql= "SELECT * FROM users WHERE email='$email';"
                    val rs=statement.executeQuery(sql)
                    val user=User()
                    while (rs.next()){
                        user.id=rs.getLong("id")
                        user.name=rs.getString("name")
                        user.email=rs.getString("email")
                        user.password=rs.getString("password")
                        user.phone=rs.getString("phone")
                        user.address=rs.getString("address")
                    }
                    user
                }
            }
        }catch (e:SQLException) {
            null
        }
    }
}