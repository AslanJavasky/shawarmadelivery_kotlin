package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.User

interface UserRepo {
    fun saveUser(user: User): User
    fun deleteUser(user: User)
    fun updateUser(user: User): User
    fun getUserByEmail(email: String): User?
}