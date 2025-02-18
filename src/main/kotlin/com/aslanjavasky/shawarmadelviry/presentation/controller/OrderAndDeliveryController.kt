package com.aslanjavasky.shawarmadelviry.presentation.controller

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery
import com.aslanjavasky.shawarmadelviry.domain.model.Order
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.*
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.OrderDto
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.util.logging.Logger

@Controller
class OrderAndDeliveryController(
    private val userService: UserService,
    private val orderService: OrderService,
    private val deliveryService: DeliveryService,
    private val sessionInfoService: SessionInfoService
) {

    @GetMapping("/order")
    fun showOrderForm(model: Model): String {
        model.addAttribute(
            "orderDto", OrderDto(
                username = sessionInfoService.username,
                address = sessionInfoService.address,
                phone = sessionInfoService.phone
            )
        )
        model.addAttribute("sessionInfoService", sessionInfoService)
        return "order"
    }


    @PostMapping("/order/submit")
    fun orderSubmit(
        @Valid @ModelAttribute("orderDto") orderDto: OrderDto,
        result: BindingResult,
        model: Model
    ): String {

        if (result.hasErrors()) {
            model.addAttribute("sessionInfoService", sessionInfoService)
            model.addAttribute("orderDto", orderDto)
            return "order"
        }

        sessionInfoService.setInfoFromOrderDto(orderDto)

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
        val savedOrder = orderService.createOrder(order)
        val delivery = Delivery(
            dateTime = LocalDateTime.now(),
            address = sessionInfoService.address,
            phone = sessionInfoService.phone,
            order = savedOrder
        )
        deliveryService.createDelivery(delivery)
        userService.updateUser(order.user!!)



        return "redirect:/menu"
    }
}


