package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.SessionScope
import java.math.BigDecimal

@Service
@SessionScope
data class SessionInfoService(
    var username: String? = "",
    var phone: String? = "",
    var address: String? = "",
    var email: String? = "",
    var cart: List<MenuItem?> = emptyList()
) {
    fun setUserInfo(user: User) {
        username = user.name
        phone = user.phone
        address = user.address
        email = user.email
    }

    fun getTotalPrice() = cart.map { it!!.price }.fold(BigDecimal.ZERO) { acc, price ->
        acc.add(price)
    }
}