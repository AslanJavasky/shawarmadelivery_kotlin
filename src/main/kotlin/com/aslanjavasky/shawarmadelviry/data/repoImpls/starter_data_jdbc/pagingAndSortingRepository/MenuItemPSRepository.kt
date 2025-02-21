package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface MenuItemPSRepository : PagingAndSortingRepository<MenuItemEntity, Long>,
    MenuItemRepository {

    override fun findAll(pageable: Pageable): Page<MenuItemEntity>

    override fun findAll(sort: Sort): MutableIterable<MenuItemEntity>

    fun findByMenuSection(menuSection: MenuSection, pageable: Pageable): Page<MenuItemEntity>

    fun findByMenuSectionOrderByPriceAsc(menuSection: MenuSection, sort: Sort): List<MenuItemEntity>

    fun findByPriceLessThanEqual(price: BigDecimal, pageable: Pageable): Page<MenuItemEntity>

    fun findByPriceGreaterThanEqualOrderByNameAsc(price:BigDecimal, sort: Sort) : List<MenuItemEntity>
}