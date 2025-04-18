//package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.adapter
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.UserEntity
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.toIUser
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.toUserEntity
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.UserRedisRepository
//import com.aslanjavasky.shawarmadelviry.domain.model.IUser
//import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
//import org.springframework.stereotype.Component
//import java.util.UUID
//
//@Component("UserRepoAdapter_Redis")
//class UserRepoAdapter(
//    private val userRepository: UserRedisRepository
//) : UserRepo {
//
//    override fun saveUser(user: IUser): IUser {
//        return userRepository.save(user.toUserEntity()).toIUser()
//    }
//
//    override fun deleteUser(user: IUser) {
//        userRepository.delete(user.toUserEntity())
//    }
//
//    override fun deleteUserByEmail(email: String) {
//        val userEntity: UserEntity? = findUserByEmail(email)
//        userEntity?.let { userRepository.delete(it) } ?: throw RuntimeException("User not found with email:$email")
//    }
//
//    override fun updateUser(user: IUser): IUser {
//        return userRepository.save(user.toUserEntity()).toIUser()
//    }
//
//    override fun getUserByEmail(email: String): IUser? {
//        val userEntity = userRepository.findByEmail(email)
//        return userEntity?.let { it.toIUser() } ?: throw RuntimeException("User not found with email:$email")
//
//    }
//
//    private fun findUserByEmail(email: String): UserEntity? = userRepository.findByEmail(email)
//
//
//    fun getUserById(uuid: UUID): IUser? = userRepository.findById(uuid).orElse(null)?.toIUser()
//}