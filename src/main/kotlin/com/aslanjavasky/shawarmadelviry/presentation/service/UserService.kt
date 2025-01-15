package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl
import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo

class UserService(
    private val repo: UserRepo
) : UserInterractor(repo) {


}