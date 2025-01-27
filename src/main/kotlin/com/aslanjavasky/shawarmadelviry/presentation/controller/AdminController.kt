package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.presentation.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/admin")
class AdminController(
    private val orderService: OrderService
) {

    @GetMapping
    fun showAdminPanel() = "admin"

    @PostMapping("/orders/update/{id}")
    fun updateOrderStatus(
        @PathVariable id:Long,
        @RequestParam status: OrderStatus): String {
       orderService.updateOrderStatus(id,status)
        return "redirect:/admin"
    }

    @GetMapping("/orders/filter")
    fun getOrdersFileterdByStatus(
        @RequestParam status: OrderStatus,
        model:Model
    ): String {
        model.addAttribute("filteredOrders", orderService.getOrdersByStatus(status))
        return "admin"
    }


    @ModelAttribute(name = "newOrders")
    fun getNewOrders() = orderService.getOrdersByStatus(OrderStatus.NEW)

}