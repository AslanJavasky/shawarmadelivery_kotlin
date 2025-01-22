package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository("URwAL")
class UserRepoImpl : UserRepo {

    private val users = mutableListOf<User>()
    private val log=LoggerFactory.getLogger(UserRepoImpl::class.java)

    override fun saveUser(user: User): User {
        users.add(user)
        log.info("User created!")
        return user
    }

    override fun deleteUser(user: User) {
        log.info("User deleted!")
        users.remove(user)
    }

    override fun updateUser(user: User): User {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
        return user
    }

    override fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}