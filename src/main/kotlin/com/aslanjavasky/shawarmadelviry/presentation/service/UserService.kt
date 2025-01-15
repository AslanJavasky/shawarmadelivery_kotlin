package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl
import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class UserService : UserInterractor(null), ApplicationContextAware {

    private lateinit var ctx: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.ctx = applicationContext
        super.repo = ctx.getBean("URwAL", UserRepo::class.java)
    }

}