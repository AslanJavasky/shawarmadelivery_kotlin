package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuItemCassandraRepository : CassandraRepository<MenuItemEntity, Long>{
    @Query("SELECT * FROM menu_items WHERE menu_item=?0 ALLOW FILTERING")
    fun findByMenuSection(section: MenuSection): List<MenuItemEntity>
}