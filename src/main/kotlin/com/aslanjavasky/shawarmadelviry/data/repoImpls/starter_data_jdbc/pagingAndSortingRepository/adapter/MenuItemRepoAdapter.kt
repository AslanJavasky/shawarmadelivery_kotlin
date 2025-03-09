package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toIMenuItem
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.toMenuItemEntity
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component("MenuItemRepoAdapter_PageSortING")
class MenuItemRepoAdapter(
   @Qualifier("MenuItemRepoExtPSRepo")  private val repo: MenuItemPSRepository
) : MenuItemRepo {


    @Transactional
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

    @Transactional
    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        return repo.save(menuItem.toMenuItemEntity()).toIMenuItem()
    }

    @Transactional
    override fun getMenuItemById(id: Long): IMenuItem? {
        return repo.findById(id).map { it.toIMenuItem() }.orElse(null)
    }

    @Transactional
    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        return repo.getMenuItemsByMenuSection(section).map { it.toIMenuItem() }
    }

    @Transactional
    override fun deleteMenuItem(menuItem: IMenuItem) {
        repo.delete(menuItem.toMenuItemEntity())
    }

    @Transactional
    override fun deleteAll() {
        repo.deleteAll()
    }

    @Transactional
    fun getAllMenuItems(pageable: Pageable): Page<IMenuItem> =
        repo.findAll(pageable).map { it.toIMenuItem() }

    @Transactional
    fun getAllMenuItems(sort: Sort): List<IMenuItem> =
        repo.findAll(sort).map { it.toIMenuItem() }

    @Transactional
    fun getMenuItemsBySection(menuSection: MenuSection, pageable: Pageable): Page<IMenuItem> =
        repo.findByMenuSection(menuSection, pageable).map { it.toIMenuItem() }

    @Transactional
    fun getMenuItemsBySectionOrderByPriceAsc(menuSection: MenuSection, sort: Sort): List<IMenuItem> =
        repo.findByMenuSectionOrderByPriceAsc(menuSection, sort)

    @Transactional
    fun getMenuItemsByOrderLessThanEqual(price:BigDecimal, pageable: Pageable) : Page<IMenuItem> =
        repo.findByPriceLessThanEqual(price, pageable).map { it.toIMenuItem() }

    @Transactional
    fun getMenuItemsByPriceGreaterThanEqualOrderByNameAsc(price: BigDecimal, sort: Sort):List<IMenuItem> =
        repo.findByPriceGreaterThanEqualOrderByNameAsc(price, sort).map { it.toIMenuItem() }

}