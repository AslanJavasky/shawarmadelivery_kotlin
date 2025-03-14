package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.UserEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserCassandraRepository : CassandraRepository<UserEntity, UUID> {
    @Query("SELECT * FROM users WHERE email=?0 ALLOW FILTERING")
    fun findByEmail(email: String):UserEntity?
}