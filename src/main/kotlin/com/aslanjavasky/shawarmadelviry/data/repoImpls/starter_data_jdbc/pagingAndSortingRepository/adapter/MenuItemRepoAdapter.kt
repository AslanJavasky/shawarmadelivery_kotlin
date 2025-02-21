package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component("MenuItemRepoAdapter_PageSortING")
class MenuItemRepoAdapter(
    private val repo: MenuItemPSRepository
) : MenuItemRepo {
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

    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }

    override fun getMenuItemById(id: Long): IMenuItem? {
        return repo.findById(id).map { it.toIMenuItem() }.orElse(null)
    }

    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        return repo.getMenuItemsByMenuSection(section).map { it.toIMenuItem() }
    }

    override fun deleteMenuItem(menuItem: IMenuItem) {
        repo.delete(menuItem.toMenuItemEntity())
    }

    override fun deleteAll() {
        repo.deleteAll()
    }

    fun getAllMenuItems(pageable: Pageable): Page<IMenuItem> =
        repo.findAll(pageable).map { it.toIMenuItem() }

    fun getAllMenuItems(sort: Sort): List<IMenuItem> =
        repo.findAll(sort).map { it.toIMenuItem() }

    fun getMenuItemsBySection(menuSection: MenuSection, pageable: Pageable): Page<IMenuItem> =
        repo.findByMenuSection(menuSection, pageable).map { it.toIMenuItem() }

    fun getMenuItemsBySectionOrderByPriceAsc(menuSection: MenuSection, sort: Sort): List<IMenuItem> =
        repo.findByMenuSectionOrderByPriceAsc(menuSection, sort)

    fun getMenuItemsByOrderLessThanEqual(price:BigDecimal, pageable: Pageable) : Page<IMenuItem> =
        repo.findByPriceLessThanEqual(price, pageable).map { it.toIMenuItem() }

    fun getMenuItemsByPriceGreaterThanEqualOrderByNameAsc(price: BigDecimal, sort: Sort):List<IMenuItem> =
        repo.findByPriceGreaterThanEqualOrderByNameAsc(price, sort).map { it.toIMenuItem() }

}