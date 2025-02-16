package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository("MRwPS")
class MenuItemRepoImpl(
    private val datasource: DataSource
) : MenuItemRepo {
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = ("INSERT INTO menu_items (id, name, menu_section, price) VALUES(?,?,?,?) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name=EXCLUDED.name,menu_section=EXCLUDED.menu_section,price=EXCLUDED.price")
        datasource.connection.use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setLong(1, menuItem.id!!)
                ps.setString(2, menuItem.name)
                ps.setString(3, menuItem.menuSection.name)
                ps.setBigDecimal(4, menuItem.price)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to save menu item, no rows affected")

                ps.generatedKeys.use { rs ->
                    while (rs.next()) {
                        menuItem.id = rs.getLong("id")
                    }
                }

            }
        }
        return menuItem
    }

    override fun updateMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = "UPDATE menu_items SET name=?, menu_section=?, price=? WHERE id=?;"
        datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, menuItem.name)
                ps.setString(2, menuItem.menuSection.name)
                ps.setBigDecimal(3, menuItem.price)
                ps.setLong(4, menuItem.id!!)
                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to update menu item, no rows affected")
            }
        }
        return menuItem
    }

    override fun getMenuItemById(id: Long): IMenuItem? {
        val sql = "SELECT * FROM menu_items WHERE id=?;"
        return datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, id)
                val menuItem = MenuItem()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        menuItem.id = rs.getLong("id")
                        menuItem.name = rs.getString("name")
                        menuItem.menuSection = MenuSection.valueOf(rs.getString("menu_section"))
                        menuItem.price = rs.getBigDecimal("price")
                    }
                }
                menuItem
            }
        }
    }

    override fun getMenuItemsBySection(section: MenuSection): List<IMenuItem> {
        val sql = "SELECT * FROM menu_items WHERE menu_section = ?"
        return datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, section.name)
                val menuItems = mutableListOf<IMenuItem>()
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        val menuItem = MenuItem()
                        menuItem.id = rs.getLong("id")
                        menuItem.name = rs.getString("name")
                        menuItem.menuSection = MenuSection.valueOf(rs.getString("menu_section"))
                        menuItem.price = rs.getBigDecimal("price")
                        menuItems.add(menuItem)
                    }
                }
                menuItems
            }
        }
    }

    override fun deleteMenuItem(menuItem: IMenuItem) {
        val sql = "DELETE FROM menu_items WHERE id=?"
        datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setLong(1, menuItem.id!!)

                val affectedRow = ps.executeUpdate()
                if (affectedRow == 0) throw SQLException("Failed to delete menu item, no rows affected")
            }
        }
    }

    override fun deleteAll() {
        val sql = "DELETE FROM menu_items"
        datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.executeUpdate()
            }
        }
    }
}