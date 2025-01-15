package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo

class MenuItemRepoImpl : MenuItemRepo {

    private val items = mutableListOf<MenuItem>()

    override fun saveMenuItem(menuItem: MenuItem): MenuItem {
        items.add(menuItem)
        return menuItem
    }

    override fun updateMenuItem(menuItem: MenuItem): MenuItem {
        val index = items.indexOfFirst { it.id == menuItem.id }
        if (index != -1) items[index] = menuItem
        return menuItem
    }

    override fun getMenuItemById(id: Long): MenuItem? {
        return items.find { it.id == id }
    }

    override fun getMenuItemsBySection(section: MenuSection): List<MenuItem> {
        return items.filter { it.menuSection.name == section.name }
    }

    override fun deleteMenuItem(menuItem: MenuItem) {
        items.remove(menuItem)
    }
}