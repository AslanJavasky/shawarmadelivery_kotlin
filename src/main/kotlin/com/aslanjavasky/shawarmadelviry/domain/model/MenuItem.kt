package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal

data class MenuItem(
    var id: Long,
    var name: String,
    var menuSection: MenuSection,
    var price: BigDecimal
)
