//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq
//
//import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
//import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
//import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.MENU_ITEMS
//import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.MenuItems
//import org.jooq.DSLContext
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.jdbc.core.BeanPropertyRowMapper
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.core.PreparedStatementCreator
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
//import org.springframework.jdbc.support.GeneratedKeyHolder
//import org.springframework.stereotype.Repository
//import java.sql.SQLException
//import java.sql.Statement
//import javax.sql.DataSource
//
//@Repository("MRwJOOQ")
//class MenuItemRepoImpl(
//    private val dslContext: DSLContext
//) : MenuItemRepo {
//    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
//
//        val affectedRow = dslContext.insertInto(MENU_ITEMS)
//            .set(MENU_ITEMS.ID, menuItem.id)
//            .set(MENU_ITEMS.NAME, menuItem.name)
//            .set(MENU_ITEMS.MENU_SECTION, menuItem.menuSection.name)
//            .set(MENU_ITEMS.PRICE, menuItem.price)
//            .onDuplicateKeyUpdate()
//            .set(MENU_ITEMS.NAME, menuItem.name)
//            .set(MENU_ITEMS.MENU_SECTION, menuItem.menuSection.name)
//            .set(MENU_ITEMS.PRICE, menuItem.price)
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to save menu item, no rows affected")
//        return menuItem
//    }
//
//    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
//
//        val affectedRow = dslContext.update(MENU_ITEMS)
//            .set(MENU_ITEMS.NAME, menuItem.name)
//            .set(MENU_ITEMS.MENU_SECTION, menuItem.menuSection.name)
//            .set(MENU_ITEMS.PRICE, menuItem.price)
//            .where(MENU_ITEMS.ID.eq(menuItem.id))
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to update menu item, no rows affected")
//        return menuItem
//    }
//
//    override fun getMenuItemById(id: Long): IMenuItem? {
//
//        return dslContext.selectFrom(MENU_ITEMS)
//            .where(MENU_ITEMS.ID.eq(id))
//            .fetchOneInto(MenuItem::class.java)
//    }
//
//    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
//
//        return dslContext.selectFrom(MENU_ITEMS)
//            .where(MENU_ITEMS.MENU_SECTION.eq(section.name))
//            .fetchInto(MenuItem::class.java)
//    }
//
//    override fun deleteMenuItem(menuItem: IMenuItem) {
//        val affectedRow =dslContext.deleteFrom(MENU_ITEMS)
//            .where(MENU_ITEMS.ID.eq(menuItem.id))
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to delete menu item, no rows affected")
//    }
//
//    override fun deleteAll() {
//        val affectedRow = dslContext.deleteFrom(MENU_ITEMS).execute()
////        if (affectedRow == 0) throw RuntimeException("Failed to delete all menu items, no rows affected")
//    }
//}