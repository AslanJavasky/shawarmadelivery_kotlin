package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl
import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class UserService(
    repo: UserRepo
) : UserInterractor(repo)