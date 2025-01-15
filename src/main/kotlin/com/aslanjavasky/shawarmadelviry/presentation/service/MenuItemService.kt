package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.interractor.MenuItemInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo

class MenuItemService(
    private val repo: MenuItemRepo
) : MenuItemInterractor(repo) {
}