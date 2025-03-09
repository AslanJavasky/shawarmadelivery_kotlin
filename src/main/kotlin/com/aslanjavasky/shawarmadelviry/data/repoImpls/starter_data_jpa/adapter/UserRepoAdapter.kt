package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toUserEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.UserJpaRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("UserRepoAdapter_JPA")
class UserRepoAdapter (
    private val userRepository: UserJpaRepository
) : UserRepo {

    @Transactional
    override fun saveUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    @Transactional
    override fun deleteUser(user: IUser) = userRepository.delete(user.toUserEntity())

    @Transactional
    override fun deleteUserByEmail(email: String) {
        userRepository.deleteByEmail(email)
    }

    @Transactional
    override fun updateUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    @Transactional
    override fun getUserByEmail(email: String) = userRepository.findByEmail(email).toIUser()

    @Transactional
    fun getUserById(id: Long) = userRepository.findById(id).map { it.toIUser() }.orElse(null)

}