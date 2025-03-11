//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq
//
//import com.aslanjavasky.shawarmadelviry.domain.model.IUser
//import com.aslanjavasky.shawarmadelviry.domain.model.User
//import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
//import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.Users.USERS
//import org.jooq.DSLContext
//import org.springframework.stereotype.Repository
//
//@Repository("URwJOOQ")
//class UserRepoImpl(
//    private val dslContext: DSLContext
//) : UserRepo {
//
//    override fun saveUser(user: IUser): IUser {
//
//        val record = dslContext.insertInto(USERS)
//            .set(USERS.NAME,user.name)
//            .set(USERS.EMAIL,user.email)
//            .set(USERS.PASSWORD,user.password)
//            .set(USERS.TELEGRAM,user.telegram)
//            .set(USERS.PHONE,user.phone)
//            .set(USERS.ADDRESS,user.address)
//            .returning(USERS.ID)
//            .fetchOne()
//
//        user.id = record!!.id
//        return user
//    }
//
//    override fun deleteUser(user: IUser) {
//
//        val affectedRow = dslContext.deleteFrom(USERS).where(USERS.ID.eq(user.id)).execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to delete user")
//
//    }
//
//
//    override fun deleteUserByEmail(email: String) {
//        val affectedRow = dslContext.delete(USERS).where(USERS.EMAIL.eq(email)).execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to delete user")
//    }
//
//
//    override fun updateUser(user: IUser): IUser {
//
//        val affectedRow = dslContext.update(USERS)
//            .set(USERS.NAME, user.name)
//            .set(USERS.EMAIL, user.email)
//            .set(USERS.PASSWORD, user.password)
//            .set(USERS.TELEGRAM, user.telegram)
//            .set(USERS.PHONE, user.phone)
//            .set(USERS.ADDRESS, user.address)
//            .where(USERS.ID.eq(user.id))
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to update user, no rows affected")
//        return user
//    }
//
//
//    override fun getUserByEmail(email: String): IUser? {
//
//        return dslContext.selectFrom(USERS).where(USERS.EMAIL.eq(email)).fetchOneInto(User::class.java)
//    }
//
//    fun getUserById(id: Long): IUser? {
//        return dslContext.selectFrom(USERS).where(USERS.ID.eq(id)).fetchOneInto(User::class.java)
//    }
//}
