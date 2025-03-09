package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toUserEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("UserRepoAdapter_PageSortING")
class UserRepoAdapter(
    private val userRepository: UserPSRepository
) : UserRepo {


    @Transactional
    override fun saveUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    @Transactional
    override fun deleteUser(user: IUser) = userRepository.delete(user.toUserEntity())

    @Transactional
    override fun deleteUserByEmail(email: String) {
        val userEntity = userRepository.getUserByEmail(email)
        if (userEntity != null) {
            userRepository.delete(userEntity)
        } else {
            throw IllegalArgumentException("User with email: $email not found")
        }
    }

    @Transactional
    override fun updateUser(user: IUser) = userRepository.save(user.toUserEntity()).toIUser()

    @Transactional
    override fun getUserByEmail(email: String) = userRepository.getUserByEmail(email).toIUser()

    @Transactional
    fun getUserById(id: Long) = userRepository.findById(id).map { it.toIUser() }.orElse(null)

    @Transactional
    fun getAllUsers(pageable: Pageable): Page<IUser> =
        userRepository.findAll(pageable).map { it.toIUser() }

    @Transactional
    fun getAllUsers(sort:Sort): List<IUser> = userRepository.findAll(sort).map { it.toIUser() }

    @Transactional
    fun getAllUserByEmailContaining(email: String, pageable: Pageable) : Page<IUser> =
        userRepository.findByEmailContaining(email, pageable).map { it.toIUser() }

    @Transactional
    fun getAllUsersOrderByNameAsc() : List<IUser> =
        userRepository.findAllByOrderByNameAsc().map { it.toIUser() }

    @Transactional
    fun getAllUsersOrderByNameAsc(pageable: Pageable) : Page<IUser> =
        userRepository.findAllByOrderByNameAsc(pageable).map { it.toIUser() }




}
