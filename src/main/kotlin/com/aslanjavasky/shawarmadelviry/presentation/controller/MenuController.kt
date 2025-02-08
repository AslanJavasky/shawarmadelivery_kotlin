package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/menu")
class MenuController(
    private val menuservice: MenuItemService,
    private val sessionInfoService: SessionInfoService
) {

    @GetMapping
    fun showMenu(model: Model): String {
        val menuItemsBySection = mapOf(
            "menu.main_menu" to menuservice.getMenuItemsBySection(MenuSection.MAIN_MENU),
            "menu.zakuski" to menuservice.getMenuItemsBySection(MenuSection.ZAKUSKI),
            "menu.dobavki" to menuservice.getMenuItemsBySection(MenuSection.DOBAVKI),
            "menu.sauce" to menuservice.getMenuItemsBySection(MenuSection.SAUCE)
        )
        model.addAttribute("menuItemsBySection", menuItemsBySection)
        return "menu"
    }

    @PostMapping("/order")
    fun processOrderForm(
        @RequestParam(required = false) selectedId: List<Long>?,
        @RequestParam(required = false) quantities: List<Int>?,
        model: Model
    ): String {

        if (selectedId.isNullOrEmpty()){
            model.addAttribute("error", "Please, select at least 1 item to place an order.")
            return showMenu(model)
        }

        val selectedMenuItems:MutableList<IMenuItem>? = selectedId!!.flatMapIndexed { index, id ->
            List(quantities!![index]) {
                menuservice.getMenuItemById(id)
            }
        }?.filterNotNull()?.toMutableList()

        sessionInfoService.cart = selectedMenuItems
        model.addAttribute("sessionInfoService", sessionInfoService)
        return "redirect:/order"
    }
}