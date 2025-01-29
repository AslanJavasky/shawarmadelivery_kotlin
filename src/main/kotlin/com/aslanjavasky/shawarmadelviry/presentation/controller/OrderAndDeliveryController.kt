package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
class OrderAndDeliveryController(
    private val menuItemService: MenuItemService,
    private val userService: UserService,
    private val orderService: OrderService,
    private val deliveryService: DeliveryService,
    private val sessionInfoService: SessionInfoService
) {

    @GetMapping("/order")
    fun showOrderForm(model: Model): String {
        if (sessionInfoService.cart == null || sessionInfoService.cart.isEmpty()) {
            return "redirect:/menu"
        }
        model.addAttribute("sessionInfoService", sessionInfoService)
        return "order"
    }

    @PostMapping("/order")
    fun processOrderForm(
        @RequestParam selectedId: List<Long>,
        @RequestParam quantities: List<Int>,
        model: Model
    ): String {
        val selectedMenuItems = selectedId.flatMapIndexed { index, id ->
            List(quantities[index]) {
                menuItemService.getMenuItemById(id)
            }
        }
        sessionInfoService.cart = selectedMenuItems
        model.addAttribute("sessionInfoService", sessionInfoService)
        return "order"
    }

    @PostMapping("/order/submit")
    fun orderSubmit(): String {

        val user = userService.getUserByEmail(sessionInfoService.email!!)?.apply {
            name = sessionInfoService.username
            address = sessionInfoService.address
            phone = sessionInfoService.phone
        }

        val order = Order(
            status = OrderStatus.NEW,
            itemList = sessionInfoService.cart,
            totalPrice = sessionInfoService.getTotalPrice(),
            dateTime = LocalDateTime.now(),
            user = user
        )
        val delivery = Delivery(
            dateTime = LocalDateTime.now(),
            address = sessionInfoService.address,
            phone = sessionInfoService.phone,
            order = order
        )

        userService.createUser(order.user!!)
        orderService.createOrder(delivery.order!!)
        deliveryService.createDelivery(delivery)
        return "redirect:/menu"
    }
}


