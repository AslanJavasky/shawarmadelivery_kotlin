package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParameterJdbcTemplate

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository("MRwNPJT")
class MenuItemRepoImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : MenuItemRepo {
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = ("INSERT INTO menu_items (id, name, menu_section, price) " +
                "VALUES(:id , :name, :menu_section, :price) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name=EXCLUDED.name,menu_section=EXCLUDED.menu_section,price=EXCLUDED.price")
        val affectedRow = namedParameterJdbcTemplate.update(sql, BeanPropertySqlParameterSource(menuItem))
        if (affectedRow == 0) throw RuntimeException("Failed to save menu item, no rows affected")
        return menuItem
    }

    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = "UPDATE menu_items SET name = :name, menu_section = :Menu_section, " +
                "price = :price WHERE id = :id;"
        val affectedRow =
            namedParameterJdbcTemplate.update(sql, BeanPropertySqlParameterSource(menuItem))
        if (affectedRow == 0) throw RuntimeException("Failed to update menu item, no rows affected")
        return menuItem
    }

    override fun getMenuItemById(id: Long): IMenuItem? {
        val sql = "SELECT * FROM menu_items WHERE id = :id;"
        return namedParameterJdbcTemplate.queryForObject(
            sql,
            MapSqlParameterSource("id", id),
            BeanPropertyRowMapper(MenuItem::class.java)
        )
    }

    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        val sql = "SELECT * FROM menu_items WHERE menu_section = :menu_section"
        return namedParameterJdbcTemplate.query(
            sql,
            MapSqlParameterSource("menu_section", section.name),
            BeanPropertyRowMapper(MenuItem::class.java)
        )
    }

    override fun deleteMenuItem(menuItem: IMenuItem) {
        val sql = "DELETE FROM menu_items WHERE id = :id"
        val affectedRow =
            namedParameterJdbcTemplate.update(sql, MapSqlParameterSource("id", menuItem.id))
        if (affectedRow == 0) throw RuntimeException("Failed to delete menu item, no rows affected")

//        val affectedRow = jdbcTemplate.update(sql){ ps ->
//            ps.setLong(1,menuItem.id )
//        }
//        if (affectedRow == 0) throw RuntimeException("Failed to delete menu item, no rows affected")
    }

    override fun deleteAll() {
        val sql = "DELETE FROM menu_items"
        val affectedRow = namedParameterJdbcTemplate.update(sql, emptyMap<String, Any>())
        if (affectedRow == 0) throw RuntimeException("Failed to delete all menu items, no rows affected")
    }
}