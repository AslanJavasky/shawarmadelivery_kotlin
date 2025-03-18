package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.MenuItemCassandraRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.getUUIDFromLong
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Component

@Component("MenuItemRepoAdapter_Cassandra")
class MenuItemRepoAdapter(
    private val repo: MenuItemCassandraRepository
) : MenuItemRepo {


    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }


    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        return saveMenuItem(menuItem)
    }


    override fun getMenuItemById(id: Long): IMenuItem? {
        return repo.findById(id.getUUIDFromLong()).map { it.toIMenuItem() }.orElse(null)
    }

    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        return repo.findByMenuSection(section).map { it.toIMenuItem() }
    }


    override fun deleteMenuItem(menuItem: IMenuItem) {
        repo.delete(menuItem.toMenuItemEntity())
    }

    override fun deleteAll() {
        repo.deleteAll()
    }
}