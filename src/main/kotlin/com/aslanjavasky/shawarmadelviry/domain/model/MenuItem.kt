package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal

data class MenuItem(
    override var id: Long,
    override var name: String,
    override var menuSection: MenuSection,
    override var price: BigDecimal
) : IMenuItem
