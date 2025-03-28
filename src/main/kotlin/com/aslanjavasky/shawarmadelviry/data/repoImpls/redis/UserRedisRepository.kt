package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis

import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRedisRepository : CrudRepository<UserEntity, UUID> {
    fun findByEmail(email: String):UserEntity?
}