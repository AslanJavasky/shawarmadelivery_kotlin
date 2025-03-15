package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.math.BigDecimal
import java.util.*

@Table("menu_items")
data class MenuItemEntity(
    @PrimaryKey
    var id: UUID? = UUID.randomUUID(),

    @Column
    var name: String = "",

    @Column
    var menuSection: MenuSection = MenuSection.MAIN_MENU,

    @Column
    var price: BigDecimal = BigDecimal.ZERO

)

fun IMenuItem.toMenuItemEntity() = MenuItemEntity(
    id = if (this.id == null) UUID.randomUUID() else UUID(this.id!!, (this.id!! shl 32) or (this.id!! ushr 32)),
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)

fun MenuItemEntity.toIMenuItem() = MenuItem(
    id = this.id!!.mostSignificantBits,
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)
