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

@Component("UserRepoAdapter_PageSortING")
class UserRepoAdapter(
    private val userRepository: UserPSRepository
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

    fun getAllUsers(pageable: Pageable): Page<IUser> =
        userRepository.findAll(pageable).map { it.toIUser() }

    fun getAllUsers(sort:Sort): List<IUser> = userRepository.findAll(sort).map { it.toIUser() }

    fun getAllUserByEmailContaining(email: String, pageable: Pageable) : Page<IUser> =
        userRepository.findByEmailContaining(email, pageable).map { it.toIUser() }

    fun getAllUsersOrderByNameAsc() : List<IUser> =
        userRepository.findAllByOrderByNameAsc().map { it.toIUser() }

    fun getAllUsersOrderByNameAsc(pageable: Pageable) : Page<IUser> =
        userRepository.findAllByOrderByNameAsc(pageable).map { it.toIUser() }




}
