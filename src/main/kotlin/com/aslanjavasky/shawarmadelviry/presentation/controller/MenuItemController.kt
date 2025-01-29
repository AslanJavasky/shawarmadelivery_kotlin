package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/menu")
class MenuItemController(
    private val service: MenuItemService
) {

    @GetMapping
    fun showMenu(model: Model): String {
        val menuItemsBySection = mapOf(
            "menu.main_menu" to service.getMenuItemsBySection(MenuSection.MAIN_MENU),
            "menu.zakuski" to service.getMenuItemsBySection(MenuSection.ZAKUSKI),
            "menu.dobavki" to service.getMenuItemsBySection(MenuSection.DOBAVKI),
            "menu.sauce" to service.getMenuItemsBySection(MenuSection.SAUCE)
        )
        model.addAttribute("menuItemsBySection", menuItemsBySection)
        return "menu"
    }
}