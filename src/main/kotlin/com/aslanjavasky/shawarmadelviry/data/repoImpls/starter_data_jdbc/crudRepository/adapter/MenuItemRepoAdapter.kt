package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("MenuItemRepoAdapter_CRUD")
class MenuItemRepoAdapter(
    @Qualifier("MenuItemRepoExtCrudRepo") private val repo: MenuItemRepository
) : MenuItemRepo {

    @Transactional
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {

        return if (menuItem.id != null) {
            //update
            val optional = repo.findById(menuItem.id!!)
            if (optional.isPresent) {
                //update
                repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
            } else {
                //insert
                repo.insert(menuItem.id!!, menuItem.name, menuItem.menuSection, menuItem.price)
                menuItem
            }

        } else {
            //insert
            repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
        }

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
        return repo.getMenuItemsByMenuSection(section).map { it.toIMenuItem() }
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