package com.aslanjavasky.shawarmadelviry.domain.repo

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection

interface MenuItemRepo {
    fun saveMenuItem(menuItem: MenuItem): MenuItem
    fun updateMenuItem(menuItem: MenuItem): MenuItem
    fun getMenuItemById(id: Long): MenuItem?
    fun getMenuItemsBySection(section: MenuSection): List<MenuItem>
    fun deleteMenuItem(menuItem: MenuItem)
}