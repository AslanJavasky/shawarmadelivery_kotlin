package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.User

interface UserRepo {
    fun saveUser(user: IUser): IUser
    fun deleteUser(user: IUser)
    fun deleteUserByEmail(email: String)
    fun updateUser(user: IUser): IUser
    fun getUserByEmail(email: String): IUser?
}