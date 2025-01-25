package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.DeliveryService
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService
import com.aslanjavasky.shawarmadelviry.presentation.service.OrderService
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService
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
    private val deliveryService: DeliveryService
) {
    @PostMapping("/order")
    fun showOrderForm(
        @RequestParam selectedId: List<Long>,
        @RequestParam quantities: List<Int>,
        model: Model
    ): String {
        val selectedMenuItems = selectedId.flatMapIndexed { index, id ->
            List(quantities[index]) {
                menuItemService.getMenuItemById(id)
            }
        }
        val totalPrice = selectedMenuItems.sumOf { it!!.price }

        val order = Order().apply {
            status = OrderStatus.NEW
            itemList = selectedMenuItems
            this.totalPrice = totalPrice
            dateTime = LocalDateTime.now()
        }
        model.addAttribute("order",order)
        model.addAttribute("user", User())
        model.addAttribute("delivery", Delivery())
        return "order"
    }

    @PostMapping("/order/submit")
    fun orderSubmit(
        @ModelAttribute delivery: Delivery,
        @ModelAttribute user:User,
        @ModelAttribute order: Order
    ): String {
        order.user=user
        delivery.order=order
        userService.createUser(user)
        orderService.createOrder(order)
        deliveryService.createDelivery(delivery)
        return "redirect:/menu"
    }
}


