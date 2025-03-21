package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo

class UserRepoImpl : UserRepo {

    private val users = mutableListOf<User>()

    override fun saveUser(user: User): User {
        users.add(user)
        return user
    }

    override fun deleteUser(user: User) {
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