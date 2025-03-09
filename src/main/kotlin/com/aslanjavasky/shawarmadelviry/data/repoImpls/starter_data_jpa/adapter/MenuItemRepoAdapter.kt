package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.MenuItemJpaRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("MenuItemRepoAdapter_JPA")
class MenuItemRepoAdapter(
    private val repo: MenuItemJpaRepository
) : MenuItemRepo {


    @Transactional
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
            return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }

    @Transactional
    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }

    @Transactional
    override fun getMenuItemById(id: Long): IMenuItem? {
        return repo.findById(id).map { it.toIMenuItem() }.orElse(null)
    }

    @Transactional
    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        return repo.findByMenuSection(section).map { it.toIMenuItem() }
    }

    @Transactional
    override fun deleteMenuItem(menuItem: IMenuItem) {
        repo.delete(menuItem.toMenuItemEntity())
    }

    @Transactional
    override fun deleteAll() {
        repo.deleteAll()
    }
}