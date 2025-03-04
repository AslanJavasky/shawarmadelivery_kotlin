package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "menu_items")
data class MenuItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_section", nullable = false)
    var menuSection: MenuSection = MenuSection.MAIN_MENU,

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO
)

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
