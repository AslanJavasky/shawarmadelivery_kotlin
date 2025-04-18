//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.getUUIDFromLong
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.UserNeo4jRepository
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toIUser
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toUserEntity
//import com.aslanjavasky.shawarmadelviry.domain.model.IUser
//import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
//import org.springframework.stereotype.Component
//
//@Component("UserRepoAdapter_Neo4j")
//class UserRepoAdapter(
//    private val userRepository: UserNeo4jRepository
//) : UserRepo {
//
//    override fun saveUser(user: IUser): IUser {
//        return userRepository.save(user.toUserEntity()).toIUser()
//    }
//
//
//    override fun deleteUser(user: IUser) {
//        userRepository.delete(user.toUserEntity())
//    }
//
//
//    override fun deleteUserByEmail(email: String) {
//        val userEntity = userRepository.findByEmail(email)
//        if (userEntity != null) userRepository.delete(userEntity)
//    }
//
//
//    override fun getUserByEmail(email: String): IUser? {
//        return userRepository.findByEmail(email)?.toIUser()
//    }
//
//
//    override fun updateUser(user: IUser): IUser {
//        return saveUser(user)
//    }
//
//
//    fun getUserById(id: Long?): IUser? {
//        val userOptional = userRepository.findById(id.getUUIDFromLong())
//        return userOptional.map { it.toIUser() }.get()
//    }
//}
