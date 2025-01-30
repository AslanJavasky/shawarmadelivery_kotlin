package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.IUser
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.User
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.OrderDto
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.SessionScope
import java.math.BigDecimal

@Service
@SessionScope
data class SessionInfoService(
    var username: String? = "",
    var phone: String? = "",
    var address: String? = "",
    var telegram: String? = "",
    var email: String? = "",
    var cart: List<IMenuItem?> = emptyList()
) {


    fun getTotalPrice() = cart.map { it!!.price }.fold(BigDecimal.ZERO) { acc, price ->
        acc.add(price)
    }

    fun setUserFields(userDto: UserDto) {
        username = userDto.name
        phone = userDto.phone
        address = userDto.address
        email = userDto.email
        telegram = userDto.telegram
    }

    fun setInfoFromOrderDto(orderDto: OrderDto) {
        username = orderDto.username
        phone = orderDto.phone
        address = orderDto.address
    }
}