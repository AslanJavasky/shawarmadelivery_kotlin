package com.aslanjavasky.shawarmadelviry.conf

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl
import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImplWithLinkedList
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo
import com.aslanjavasky.shawarmadelviry.presentation.controller.UserController
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ShawarmaConf {

    @Bean(name = ["URwAL"])
    fun UserRepo():UserRepo = UserRepoImpl()

    @Bean("URwLL")
    fun UserRepoWithLinkedList():UserRepo = UserRepoImplWithLinkedList()

    @Bean
    fun userService() = UserService(UserRepo())

    @Bean
    fun userController(userService: UserService) = UserController(userService)

    @Bean
    fun CommandLineRunner(userController: UserController) =
        ApplicationStartupRunner(userController)

}