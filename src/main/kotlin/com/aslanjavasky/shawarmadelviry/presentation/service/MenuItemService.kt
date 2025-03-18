package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.interractor.MenuItemInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class MenuItemService(
    @Qualifier("MenuItemRepoAdapter_Cassandra") private val repo: MenuItemRepo
) : MenuItemInterractor(repo) {
}