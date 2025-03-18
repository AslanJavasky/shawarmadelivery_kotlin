package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import java.util.*

//@Repository
interface MenuItemCassandraRepository : CassandraRepository<MenuItemEntity, UUID>{
    @Query("SELECT * FROM menu_items WHERE menusection = ?0 ALLOW FILTERING")
    fun findByMenuSection(section: MenuSection): List<MenuItemEntity>
}