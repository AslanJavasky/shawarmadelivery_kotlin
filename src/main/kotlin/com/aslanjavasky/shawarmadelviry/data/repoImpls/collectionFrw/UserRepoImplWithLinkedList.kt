package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import java.util.LinkedList

class UserRepoImplWithLinkedList : UserRepo {

    private val users: MutableList<User> = LinkedList()

    override fun saveUser(user: User): User {
        users.add(user)
        println("User created in Linkedlist!")
        return user
    }

    override fun deleteUser(user: User) {
        if (users.remove(user)){
            println("User deleted in Linkedlist!")
        }else{
            println("User not found in Linkedlist!")
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