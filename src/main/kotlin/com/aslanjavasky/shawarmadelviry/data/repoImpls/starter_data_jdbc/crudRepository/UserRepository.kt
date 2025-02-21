package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.UserEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("UserRepoExtCrudRepo")
interface UserRepository : CrudRepository<UserEntity, Long> {
    fun deleteUserByEmail(email: String)
    fun getUserByEmail(email: String): UserEntity
}