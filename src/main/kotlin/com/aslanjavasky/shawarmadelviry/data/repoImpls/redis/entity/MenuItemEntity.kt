//package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity
//
//import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
//import org.springframework.data.annotation.Id
//import org.springframework.data.redis.core.RedisHash
//import org.springframework.data.redis.core.index.Indexed
//import java.io.Serializable
//import java.math.BigDecimal
//import java.util.*
//
//@RedisHash("menu_items")
//data class MenuItemEntity(
//    @Id
//    var id: UUID? = UUID.randomUUID(),
//    var name: String = "",
//    @Indexed
//    var menuSection: MenuSection = MenuSection.MAIN_MENU,
//    var price: BigDecimal = BigDecimal.ZERO
//) : Serializable
//
//
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
