package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j

import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.UserEntity
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserNeo4jRepository : Neo4jRepository<UserEntity, UUID> {
    fun findByEmail(email: String):UserEntity?
}