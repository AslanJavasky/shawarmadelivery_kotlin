package com.aslanjavasky.shawarmadelviry.domain.interractor

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo

open class MenuItemInterractor(
    private val repo: MenuItemRepo
) {
    fun saveMenuItem(menuItem: IMenuItem) = repo.saveMenuItem(menuItem)
    fun updateMenuItem(menuItem: IMenuItem) = repo.updateMenuItem(menuItem)
    fun getMenuItemsBySection(section: MenuSection) = repo.getMenuItemsBySection(section)
    fun getMenuItemById(id: Long) = repo.getMenuItemById(id)
}