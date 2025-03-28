package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb

import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MenuItemMongoRepository : MongoRepository<MenuItemEntity, UUID>{
//    @Query("{'menuSection' : ?0}")
    fun findByMenuSection(section: MenuSection): List<MenuItemEntity>
}