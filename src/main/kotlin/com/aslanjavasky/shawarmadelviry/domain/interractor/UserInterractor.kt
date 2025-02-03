package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo

open class UserInterractor(
    protected var repo: UserRepo?
) {

    fun createUser(user:IUser) = repo?.saveUser(user)
    fun updateUser(user:IUser) = repo?.updateUser(user)
    fun deleteUser(user:IUser) = repo?.deleteUser(user)
    fun deleteUserByEmail(email: String) = repo?.deleteUserByEmail(email)
    fun getUserByEmail(email:String) = repo?.getUserByEmail(email)
}