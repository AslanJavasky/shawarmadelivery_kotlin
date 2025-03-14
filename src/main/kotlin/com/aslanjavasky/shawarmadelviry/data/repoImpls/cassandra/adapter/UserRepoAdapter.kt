package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UserCassandraRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.toIUser
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.toUserEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component("UserRepoAdapter_Cassandra")
class UserRepoAdapter (
    private val userRepository: UserCassandraRepository
) : UserRepo {

    override fun saveUser(user: IUser): IUser {
        return userRepository.save(user.toUserEntity()).toIUser()
    }

    override fun deleteUser(user: IUser) {
        userRepository.delete(user.toUserEntity())
    }

    override fun deleteUserByEmail(email: String) {
        val userEntity = userRepository.findByEmail(email)
        if (userEntity != null) userRepository.delete(userEntity)
    }

    override fun updateUser(user: IUser): IUser {
        return userRepository.save(user.toUserEntity()).toIUser()
    }

    override fun getUserByEmail(email: String): IUser? {
        val userEntity = userRepository.findByEmail(email)
        return userEntity?.toIUser() ?: null
    }

    fun getUserById(uuid: UUID) = userRepository.findById(uuid).orElse(null)
}