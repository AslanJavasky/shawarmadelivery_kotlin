package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository("MRwPS")
class MenuItemRepoImpl(
    private val datasource: DataSource
) : MenuItemRepo {
    override fun saveMenuItem(menuItem: IMenuItem): IMenuItem {
        val sql = "INSERT INTO menu_items (name, menu_section, price) VALUES(?,?,?)"
        datasource.connection.use { connection ->
            connection.prepareStatement(sql).use { ps ->
                ps.setString(1, menuItem.name)
                ps.setString(2, menuItem.menuSection.name)
                ps.setBigDecimal(3, menuItem.price)
                ps.executeUpdate()
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
                ps.setLong(4, menuItem.id)
                ps.executeUpdate()
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
                ps.setLong(1, menuItem.id)
                ps.executeUpdate()
            }
        }
    }
}