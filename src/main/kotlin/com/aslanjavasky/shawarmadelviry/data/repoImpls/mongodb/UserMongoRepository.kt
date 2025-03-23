package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb

import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserMongoRepository : MongoRepository<UserEntity, UUID> {
    fun findByEmail(email: String):UserEntity?
}