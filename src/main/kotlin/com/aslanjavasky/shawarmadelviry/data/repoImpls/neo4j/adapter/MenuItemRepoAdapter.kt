package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.getUUIDFromLong
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.MenuItemNeo4jRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("MenuItemRepoAdapter_Neo4j")
class MenuItemRepoAdapter(
    private val repo: MenuItemNeo4jRepository
) : MenuItemRepo {


    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
            return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }

    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
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