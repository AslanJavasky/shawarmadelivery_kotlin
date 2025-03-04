package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun deleteByEmail(email: String)
    fun findByEmail(email: String): UserEntity
}