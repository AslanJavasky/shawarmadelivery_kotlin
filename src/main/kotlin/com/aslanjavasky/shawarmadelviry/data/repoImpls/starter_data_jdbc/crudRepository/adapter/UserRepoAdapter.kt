package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.toUserEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component("UserRepoAdapter_CRUD")
class UserRepoAdapter(
    @Qualifier("UserRepoExtCrudRepo") private val userRepository: UserRepository
) : UserRepo {
    override fun saveUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    override fun deleteUser(user: IUser) = userRepository.delete(user.toUserEntity())

    override fun deleteUserByEmail(email: String) {
        val userEntity = userRepository.getUserByEmail(email)
        if (userEntity != null) {
            userRepository.delete(userEntity)
        } else {
            throw IllegalArgumentException("User with email: $email not found")
        }
    }

    override fun updateUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    override fun getUserByEmail(email: String) = userRepository.getUserByEmail(email).toIUser()

    fun getUserById(id: Long) = userRepository.findById(id).map { it.toIUser() }.orElse(null)

}