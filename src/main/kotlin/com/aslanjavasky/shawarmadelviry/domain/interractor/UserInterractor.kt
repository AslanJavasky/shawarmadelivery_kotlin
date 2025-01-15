package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo

open class UserInterractor(
    protected var repo: UserRepo?
) {

    fun createUser(user:User) = repo?.saveUser(user)
    fun updateUser(user:User) = repo?.updateUser(user)
    fun deleteUser(user:User) = repo?.deleteUser(user)
    fun getUserByEmail(email:String) = repo?.getUserByEmail(email)
}