package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection

interface MenuItemRepo {
    fun saveMenuItem(menuItem: IMenuItem): IMenuItem
    fun updateMenuItem(menuItem: IMenuItem): IMenuItem
    fun getMenuItemById(id: Long): IMenuItem?
    fun getMenuItemsBySection(section: MenuSection): List<IMenuItem>
    fun deleteMenuItem(menuItem: IMenuItem)
}