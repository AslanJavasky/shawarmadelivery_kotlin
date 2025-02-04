package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class MenuItemRepoImpl : MenuItemRepo {

    private val items = mutableListOf<IMenuItem>()
    private var nextId = AtomicLong(1)

    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        menuItem.id=nextId.getAndIncrement()
        items.add(menuItem)
        return menuItem
    }

    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        val index = items.indexOfFirst { it.id == menuItem.id }
        if (index != -1) items[index] = menuItem
        return menuItem
    }

    override fun getMenuItemById(id: Long): IMenuItem? {
        return items.find { it.id == id }
    }

    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        return items.filter { it.menuSection.name == section.name }
    }

    override fun deleteMenuItem(menuItem: IMenuItem) {
        items.remove(menuItem)
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}