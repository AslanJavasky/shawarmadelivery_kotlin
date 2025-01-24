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
            MenuSection.MAIN_MENU to service.getMenuItemsBySection(MenuSection.MAIN_MENU),
            MenuSection.ZAKUSKI to service.getMenuItemsBySection(MenuSection.ZAKUSKI),
            MenuSection.DOBAVKI to service.getMenuItemsBySection(MenuSection.DOBAVKI),
            MenuSection.SAUCE to service.getMenuItemsBySection(MenuSection.SAUCE)
        )
        model.addAttribute("menuItemsBySection", menuItemsBySection)
        return "menu"
    }
}