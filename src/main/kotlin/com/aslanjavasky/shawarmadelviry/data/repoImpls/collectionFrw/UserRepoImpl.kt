package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository("URwAL")
class UserRepoImpl : UserRepo {

    private val users = mutableListOf<IUser>()
    private val log=LoggerFactory.getLogger(UserRepoImpl::class.java)
    private var nextId = AtomicLong(1)

    override fun saveUser(user: IUser): IUser {
        user.id=nextId.getAndIncrement()
        users.add(user)
        log.info("User created!")
        return user
    }

    override fun deleteUser(user: IUser) {
        log.info("User deleted!")
        users.remove(user)
    }

    override fun updateUser(user: IUser): IUser {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
        return user
    }

    override fun getUserByEmail(email: String): IUser? {
        return users.find { it.email == email }
    }
}