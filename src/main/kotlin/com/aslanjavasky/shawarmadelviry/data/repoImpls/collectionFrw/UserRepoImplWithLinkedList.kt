package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import java.util.LinkedList
import java.util.concurrent.atomic.AtomicLong

@Repository("URwLL")
class UserRepoImplWithLinkedList : UserRepo {

    private val users: MutableList<IUser> = LinkedList()
    private val log= KotlinLogging.logger("UserRepoImplWithLinkedList")
    private var nextId = AtomicLong(1)

    override fun saveUser(user: IUser): IUser {
        user.id=nextId.getAndIncrement()
        users.add(user)
        log.info("User created in Linkedlist!")
        return user
    }

    override fun deleteUser(user: IUser) {
        if (users.remove(user)){
            log.info("User deleted in Linkedlist!")
        }else{
            log.info("User not found in Linkedlist!")
        }
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