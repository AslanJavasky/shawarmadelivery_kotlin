package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity

import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.Node
import java.math.BigDecimal
import java.util.*

@Node("MenuItem")
data class MenuItemEntity(
    @Id
    var id: UUID? = UUID.randomUUID(),
    var name: String = "",
    var menuSection: MenuSection = MenuSection.MAIN_MENU,
    var price: BigDecimal = BigDecimal.ZERO
)


//fun IMenuItem.toMenuItemEntity() = MenuItemEntity(
//    id = this.id.getUUIDFromLong(),
//    name = this.name,
//    menuSection = this.menuSection,
//    price = this.price
//)
//
//fun MenuItemEntity.toIMenuItem() = MenuItem(
//    id = this.id!!.getLongFromUUID(),
//    name = this.name,
//    menuSection = this.menuSection,
//    price = this.price
//)
