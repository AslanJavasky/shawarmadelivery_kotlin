package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPSRepository : PagingAndSortingRepository<UserEntity, Long>,
                             UserRepository {

    override fun findAll(pageable: Pageable): Page<UserEntity>

    override fun findAll(sort: Sort): List<UserEntity>

    fun findByEmailContaining(email: String, pageable: Pageable): Page<UserEntity>

    fun findAllByOrderByNameAsc(): List<UserEntity>

    fun findAllByOrderByNameAsc(pageable: Pageable): Page<UserEntity>
}