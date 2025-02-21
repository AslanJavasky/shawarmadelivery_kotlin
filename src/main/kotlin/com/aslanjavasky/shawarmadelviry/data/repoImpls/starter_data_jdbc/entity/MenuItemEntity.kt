package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("menu_items")
class MenuItemEntity(
    @Id
    override var id: Long? = null,
    override var name: String,
    override var menuSection: MenuSection,
    override var price: BigDecimal
) : IMenuItem

fun IMenuItem.toMenuItemEntity() = MenuItemEntity(
    id = this.id,
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)

fun MenuItemEntity.toIMenuItem() = MenuItem(
    id = this.id!!,
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)
