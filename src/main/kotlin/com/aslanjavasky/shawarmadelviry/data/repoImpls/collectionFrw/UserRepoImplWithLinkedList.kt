package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import java.util.LinkedList

@Repository("URwLL")
class UserRepoImplWithLinkedList : UserRepo {

    private val users: MutableList<User> = LinkedList()
    private val log= KotlinLogging.logger("UserRepoImplWithLinkedList")

    override fun saveUser(user: User): User {
        users.add(user)
        log.info("User created in Linkedlist!")
        return user
    }

    override fun deleteUser(user: User) {
        if (users.remove(user)){
            log.info("User deleted in Linkedlist!")
        }else{
            log.info("User not found in Linkedlist!")
        }
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