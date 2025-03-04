package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuItemJpaRepository : JpaRepository<MenuItemEntity, Long>{
    fun findByMenuSection(section: MenuSection): List<MenuItemEntity>
}