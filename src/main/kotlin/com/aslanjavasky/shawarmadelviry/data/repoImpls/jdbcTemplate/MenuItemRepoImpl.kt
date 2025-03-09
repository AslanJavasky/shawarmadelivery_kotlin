package com.aslanjavasky.shawarmadelviry.data.repoImpls.jdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository("MRwJT")
class MenuItemRepoImpl(
    private val jdbcTemplate: JdbcTemplate
) : MenuItemRepo {

    @Transactional
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = ("INSERT INTO menu_items (id, name, menu_section, price) VALUES(?,?,?,?) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name=EXCLUDED.name,menu_section=EXCLUDED.menu_section,price=EXCLUDED.price")
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(PreparedStatementCreator { connection ->
            val ps = connection.prepareStatement(sql, arrayOf("id"))
            ps.setLong(1, menuItem.id!!)
            ps.setString(2, menuItem.name)
            ps.setString(3, menuItem.menuSection.name)
            ps.setBigDecimal(4, menuItem.price)
            ps
        }, keyHolder)
        menuItem.id = keyHolder.key?.toLong() ?: throw RuntimeException("Failed to save menuItem, no genereted key")
        return menuItem
    }

    @Transactional
    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = "UPDATE menu_items SET name=?, menu_section=?, price=? WHERE id=?;"
        val affectedRow =
            jdbcTemplate.update(sql, menuItem.name, menuItem.menuSection.name, menuItem.price, menuItem.id)
        if (affectedRow == 0) throw RuntimeException("Failed to update menu item, no rows affected")
        return menuItem
    }

    override fun getMenuItemById(id: Long): IMenuItem? {
        val sql = "SELECT * FROM menu_items WHERE id=?;"
        return jdbcTemplate.query(sql, { rs, _ ->
            MenuItem().apply {
                this.id = rs.getLong("id")
                name = rs.getString("name")
                menuSection = MenuSection.valueOf(rs.getString("menu_section"))
                price = rs.getBigDecimal("price")
            }
        }, id)[0] ?: null
    }

    @Transactional
    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        val sql = "SELECT * FROM menu_items WHERE menu_section = ?"
        return jdbcTemplate.query(sql,  { rs, _ ->
            MenuItem().apply {
                id = rs.getLong("id")
                name = rs.getString("name")
                menuSection = MenuSection.valueOf(rs.getString("menu_section"))
                price = rs.getBigDecimal("price")
            }
        }, section.name)
    }

    @Transactional
    override fun deleteMenuItem(menuItem: IMenuItem) {
        val sql = "DELETE FROM menu_items WHERE id=?"
        val affectedRow = jdbcTemplate.update(sql, menuItem.id)
        if (affectedRow == 0) throw RuntimeException("Failed to delete menu item, no rows affected")

//        val affectedRow = jdbcTemplate.update(sql){ ps ->
//            ps.setLong(1,menuItem.id )
//        }
//        if (affectedRow == 0) throw RuntimeException("Failed to delete menu item, no rows affected")
    }

    @Transactional
    override fun deleteAll() {
        val sql = "DELETE FROM menu_items"
        jdbcTemplate.update(sql)
    }
}