package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository("MenuItemRepoExtCrudRepo")
interface MenuItemRepository: CrudRepository<MenuItemEntity, Long> {
    fun getMenuItemsByMenuSection(section: MenuSection): List<MenuItemEntity>

    @Modifying
    @Query("INSERT INTO menu_items (id, name, menu_section, price) " +
            "VALUES (:id, :name, :menu_section, :price )")
    fun insert(@Param("id") id:Long, @Param("name") name:String,
               @Param("menu_section") menuSection: MenuSection, @Param("price") price:BigDecimal)
}