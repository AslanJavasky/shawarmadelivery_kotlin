package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UserService(
    @Qualifier("UserRepoAdapter_JPA") repo: UserRepo
) : UserInterractor(repo)